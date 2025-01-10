package com.msg.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.counter.model.CounterService;
import com.counter.model.CounterVO;
import com.faq.model.FaqVO;
import com.msg.model.MsgService;
import com.msg.model.MsgVO;
import com.notice.model.NoticeService;
import com.notice.model.NoticeVO;
import com.member.model.MemberVO;
import com.member.model.MemberRepository;
import com.member.model.MemberService;

@Controller
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    MsgService msgSvc;
    @Autowired
    NoticeService noticeSvc;
    
    @Autowired
    CounterService counterSvc;
    
    @Autowired 
    private MemberRepository memberRepository;
    
    @Autowired
    private MemberService memberSvc;
    
    
    @GetMapping("addMsg")
    public String addEmp(HttpSession session,ModelMap model) {
    	 CounterVO counter = (CounterVO) session.getAttribute("counter");
         if (counter == null) {
             // 處理沒有 CounterVO 的情況
             return "redirect:/counter/login"; // 假設有一個登錄頁面
         }
        MsgVO msgVO = new MsgVO();
        model.addAttribute("memberList", memberSvc.getAll());
        model.addAttribute("msgVO", msgVO);
        model.addAttribute("counter", counterSvc.getOneCounter(counter.getCounterNo()));
        model.addAttribute("msgSvc", msgSvc);
        return "vendor-end/msg/addMsg";
    }
    
    
   
    @PostMapping("/insert")
    public String insert(HttpSession session, 
                         @Valid @ModelAttribute("msgVO") MsgVO msgVO, 
                         BindingResult result, 
                         ModelMap model,
                         @RequestParam("informMsg") String informMsg,
                         @RequestParam("sendTo") String sendTo, 
                         @RequestParam(value = "memNo", required = false) Integer memNo) {

        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        CounterVO counter = (CounterVO) session.getAttribute("counter");
        
        if (counter == null) {
            return "redirect:/counter/login";
        }
        msgVO.setCounterNo(counter.getCounterNo());
        msgVO.setInformMsg(informMsg);

        if (result.hasErrors()) {
            model.addAttribute("counter", counterSvc.getOneCounter(counter.getCounterNo()));
            model.addAttribute("msgSvc", msgSvc);
            model.addAttribute("memberList", memberRepository.findAll());
            return "vendor-end/msg/addMsg";
        }

        /*************************** 2.開始新增資料 *****************************************/
        msgSvc.addMsg(msgVO);

        if ("all".equals(sendTo)) {
            // 發送給所有會員
            List<MemberVO> allMembers = memberRepository.findAll();
            for (MemberVO member : allMembers) {
                NoticeVO noticeVO = new NoticeVO();
                noticeVO.setNoticeContent(informMsg);
                noticeVO.setNoticeDate(new Timestamp(System.currentTimeMillis()));
                noticeVO.setMemNo(member.getMemNo());
                noticeSvc.save(noticeVO);
            }
        } else {
            // 發送給單個會員
            NoticeVO noticeVO = new NoticeVO();
            noticeVO.setNoticeContent(informMsg);
            noticeVO.setNoticeDate(new Timestamp(System.currentTimeMillis()));
            noticeVO.setMemNo(memNo);
            noticeSvc.save(noticeVO);
        }

        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
        model.addAttribute("success", "新增成功");
        return "redirect:/msg/listAllMsg";
    }
    

   
    @PostMapping("getOne_For_Update")
    public String getOne_For_Update(HttpSession session,@RequestParam("counterInformNo") String counterInformNo, ModelMap model) {
    	CounterVO counter = (CounterVO) session.getAttribute("counter");
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        /*************************** 2.開始查詢資料 *****************************************/
        MsgVO msgVO = msgSvc.getOneMsg(Integer.valueOf(counterInformNo));

        /*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
        model.addAttribute("counter", counterSvc.getOneCounter(counter.getCounterNo()));
        model.addAttribute("msgSvc", msgSvc);
        model.addAttribute("memberList", memberRepository.findAll());
        model.addAttribute("msgVO", msgVO);
        return "vendor-end/msg/update_msg_input"; // 查詢完成後轉交update_msg_input.html
    }
    
    
    

    
    @PostMapping("update")
    public String update(HttpSession session, 
                         @Valid @ModelAttribute("msgVO") MsgVO msgVO, 
                         BindingResult result, 
                         ModelMap model,
                         @RequestParam("informMsg") String informMsg) {
                         
        CounterVO counter = (CounterVO) session.getAttribute("counter");
        if (counter == null) {
            return "redirect:/counter/login";
        }

        msgVO.setInformMsg(informMsg); // 設置訊息內文

        if (result.hasErrors()) {
            model.addAttribute("counter", counterSvc.getOneCounter(counter.getCounterNo()));
            model.addAttribute("msgSvc", msgSvc);
            model.addAttribute("memberList", memberRepository.findAll());
            return "vendor-end/msg/update_msg_input";
        }

        // 開始修改資料
        MsgVO existingMsgVO = msgSvc.getMsgById(msgVO.getCounterInformNo());
        if (existingMsgVO == null) {
            result.rejectValue("informMsg", "error.msgVO", "消息不存在");
            return "vendor-end/msg/update_msg_input";
        }

        existingMsgVO.setInformMsg(informMsg);
        existingMsgVO.setInformDate(new Timestamp(System.currentTimeMillis()));
        existingMsgVO.setInformRead((byte) 0); // 設置為未讀狀態
        msgSvc.updateMsg(existingMsgVO);

        if (existingMsgVO.getMemNo() == null) {
            // 更新所有會員的通知信息
            List<MemberVO> allMembers = memberRepository.findAll();
            for (MemberVO member : allMembers) {
                NoticeVO noticeVO = noticeSvc.getNoticeByMsgIdAndMemNo(existingMsgVO.getCounterInformNo(), member.getMemNo());
                if (noticeVO == null) {
                    noticeVO = new NoticeVO();
                    noticeVO.setCounterInformNo(existingMsgVO.getCounterInformNo());
                    noticeVO.setMemNo(member.getMemNo());
                }

                if (!"櫃位更新了訊息: ".concat(informMsg).equals(noticeVO.getNoticeContent())) {
                    noticeVO.setNoticeContent("櫃位更新了訊息: " + informMsg);
                    noticeVO.setNoticeDate(new Timestamp(System.currentTimeMillis()));
                    noticeSvc.save(noticeVO);
                } else {
                    // 新增一條櫃位訊息修改的通知
                    NoticeVO newNoticeVO = new NoticeVO();
                    newNoticeVO.setNoticeContent("櫃位訊息已更新");
                    newNoticeVO.setNoticeDate(new Timestamp(System.currentTimeMillis()));
                    newNoticeVO.setMemNo(member.getMemNo());
                    newNoticeVO.setCounterInformNo(existingMsgVO.getCounterInformNo());
                    noticeSvc.save(newNoticeVO);
                }
            }
        } else {
            // 更新單個會員的通知信息
            NoticeVO noticeVO = noticeSvc.getNoticeByMsgIdAndMemNo(existingMsgVO.getCounterInformNo(), existingMsgVO.getMemNo());
            if (noticeVO == null) {
                noticeVO = new NoticeVO();
                noticeVO.setCounterInformNo(existingMsgVO.getCounterInformNo());
                noticeVO.setMemNo(existingMsgVO.getMemNo());
            }

            if (!"櫃位更新了訊息: ".concat(informMsg).equals(noticeVO.getNoticeContent())) {
                noticeVO.setNoticeContent("櫃位更新了訊息: " + informMsg);
                noticeVO.setNoticeDate(new Timestamp(System.currentTimeMillis()));
                noticeSvc.save(noticeVO);
            } else {
                // 新增一條櫃位訊息修改的通知
                NoticeVO newNoticeVO = new NoticeVO();
                newNoticeVO.setNoticeContent("櫃位訊息已更新");
                newNoticeVO.setNoticeDate(new Timestamp(System.currentTimeMillis()));
                newNoticeVO.setMemNo(existingMsgVO.getMemNo());
                newNoticeVO.setCounterInformNo(existingMsgVO.getCounterInformNo());
                noticeSvc.save(newNoticeVO);
            }
        }

        model.addAttribute("success", "修改成功！");
        return "redirect:/msg/listAllMsg"; 
    }




 


    
    @PostMapping("delete")
    public String delete(HttpSession session,@RequestParam("counterInformNo") String counterInformNo, ModelMap model) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        /*************************** 2.開始刪除資料 *****************************************/
        msgSvc.deleteMsg(Integer.valueOf(counterInformNo));
        /*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
        model.addAttribute("success", "- (刪除成功)");
        return "redirect:/msg/listAllMsg"; // 刪除完成後轉交listAllMsg.html
    }
 
    
    @GetMapping("listAllMsg")
    public String listAllmsg(HttpSession session, Model model) {
        CounterVO counter = (CounterVO) session.getAttribute("counter");
        if (counter == null) {
            return "redirect:/counter/login";
        } else {
            List<MsgVO> list = msgSvc.getOneCounterMsg(counter.getCounterNo());
            // 將點進訊息通知後所有訊息設置為已讀
            for (MsgVO msg : list) {
                msg.setInformRead((byte)1);
                msgSvc.updateMsg(msg); 
            }
            model.addAttribute("counter", counterSvc.getOneCounter(counter.getCounterNo()));
            model.addAttribute("counterMsgListData", list);
            model.addAttribute("msgSvc", msgSvc);
            return "vendor-end/msg/listAllMsg";
        }
    }
      
    @ModelAttribute("counterMsgListData")
    protected List<MsgVO> CounterReferenceListData(HttpSession session, Model model) {
        CounterVO counter = (CounterVO) session.getAttribute("counter");
        if (counter != null) {
        	List<MsgVO> list =  msgSvc.getOneCounterMsg(counter.getCounterNo());
            return list;
        } else {
            // 如果counter為null，返回一個空列表或處理錯誤
            model.addAttribute("error", "未登錄或Session信息遺失");
            return new ArrayList<>(); // 或者其他適當的錯誤處理
        }
    }
    
    @PostMapping("listCounterMsg_ByCompositeQuery")
    public String listCounterMsg(HttpSession session ,HttpServletRequest req, Model model) {
        CounterVO counter = (CounterVO) session.getAttribute("counter");
        List<MsgVO> list =  msgSvc.getOneCounterMsg(counter.getCounterNo());
        model.addAttribute("counterMsgListData", list); 
        return "vendor-end/msg/listAllMsg";
    }
    
    
        
    }

        


