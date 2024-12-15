package com.notice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.counter.model.CounterVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.msg.model.MsgVO;
import com.notice.model.NoticeService;
import com.notice.model.NoticeVO;


@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	
	@Autowired
    NoticeService noticeSvc;
	
	@Autowired
    MemberService memberSvc;
	
//	@GetMapping("listAllNotice")
//    public String getAllNotice(ModelMap model) {
//    	List<NoticeVO> list = noticeSvc.getAll();
//    	model.addAttribute("noticeListData", list);
//    	return "front-end/notice/listAllNotice";
//    }
	
	//柏翔新增======================================================
    @GetMapping("listAllNotice")
    public String getAllNotice(HttpSession session, ModelMap model) {
        // 從 session 取得會員編號
        Object memNoObj = session.getAttribute("memNo");
        
        try {
            if (memNoObj == null) {
                return "redirect:/mem/login";
            }
            
            // 轉換會員編號
            Integer memNo = (memNoObj instanceof Integer) ? 
                (Integer) memNoObj : Integer.parseInt((String) memNoObj);
            
            // 取得該會員的所有通知
            List<NoticeVO> list = noticeSvc.getOneMemberNotice(memNo);
            model.addAttribute("noticeListData", list);
            
            return "front-end/notice/listAllNotice";
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "載入通知時發生錯誤");
            return "error";
        }
    }
	
	
	
	 
	//柏翔新增======================================================
//	    @PostMapping("clearAll")
//	    public String clearAllNotices(HttpSession session, ModelMap model) {
//	        Object memNoObj = session.getAttribute("memNo");
//	        
//	        try {
//	            if (memNoObj == null) {
//	                return "redirect:/mem/login";
//	            }
//	            
//	            Integer memNo = (memNoObj instanceof Integer) ? 
//	                (Integer) memNoObj : Integer.parseInt((String) memNoObj);
//	                
//	            // 清除該會員的所有通知
//	            noticeSvc.deleteAllByMemNo(memNo);
//	            model.addAttribute("success", "所有通知已清空！");
//	            
//	            return "redirect:/notice/listAllNotice";
//	            
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            model.addAttribute("error", "清除通知時發生錯誤");
//	            return "error";
//	        }
//	    }
	 
	 
	 
//		會員通知總攬
//	    @GetMapping("listAllNotice")
//	    public String listAllnotice(HttpSession session, Model model) {
//	    	//櫃位優惠券登錄確認
//	        MemberVO member = (MemberVO) session.getAttribute("member");
//	        if (member == null) {
//	            return "redirect:/member/login";
//	        } else {
//	            // 其他邏輯
//	            model.addAttribute("member", memberSvc.getOneCounter(member.getMemNo()));
//	            model.addAttribute("memberNoticeListData", noticeSvc.getOneCounterMsg(member.getMemNo()));
//	            return "front-end/notice/listAllNotice";
//	        }
//	    }
//	    
//	    @ModelAttribute("memberNoticeListData")
//	    protected List<NoticeVO> MemberReferenceListData(HttpSession session, Model model) {
//	        MemberVO member = (MemberVO) session.getAttribute("member");
//	        if (member != null) {
//	        	List<MsgVO> list =  msgSvc.getOneCounterMsg(counter.getCounterNo());
//	            return list;
//	        } else {
//	            // 如果counter為null，返回一個空列表或處理錯誤
//	            model.addAttribute("error", "未登錄或Session信息遺失");
//	            return new ArrayList<>(); // 或者其他適當的錯誤處理
//	        }
//	    }
//	    
//	    @PostMapping("listCounterMsg_ByCompositeQuery")
//	    public String listCounterMsg(HttpSession session ,HttpServletRequest req, Model model) {
//	        CounterVO counter = (CounterVO) session.getAttribute("counter");
//	        List<MsgVO> list =  msgSvc.getOneCounterMsg(counter.getCounterNo());
//	        model.addAttribute("counterMsgListData", list); 
//	        return "vendor-end/msg/listAllMsg";
//	    }
//	    
	    
	    
	    
	 

	 
		//柏翔新增======================================================
	    // 標記會員的所有通知為已讀
//	    @PostMapping("/markAllRead") 
//	    @ResponseBody
//	    public ResponseEntity<Void> markAllRead(HttpSession session) {
//	        Object memNoObj = session.getAttribute("memNo");
//	        
//	        try {
//	            if (memNoObj == null) {
//	                return ResponseEntity.status(401).build(); // 未登入
//	            }
//	            
//	            Integer memNo = (memNoObj instanceof Integer) ? 
//	                (Integer) memNoObj : Integer.parseInt((String) memNoObj);
//	                
//	            // 標記該會員的所有通知為已讀
//	            noticeSvc.markAllAsReadByMemNo(memNo);
//	            return ResponseEntity.ok().build();
//	            
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return ResponseEntity.status(500).build();
//	        }
//	    }
//	 
	 
	
	 
}




    



   




   



    



   

