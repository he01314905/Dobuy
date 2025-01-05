package com.customerservice.controller;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;

import com.notice.model.NoticeService;
import com.notice.model.NoticeVO;
import com.usedorder.model.UsedOrderVO;
import com.counterorder.model.CounterOrderService;
import com.customerservice.model.CustomerServiceService;
import com.customerservice.model.CustomerServiceVO;
import com.msg.model.MsgService;
import com.msg.model.MsgVO;
import com.counter.model.*;



@Controller
@RequestMapping("/customerservice")
public class CustomerServiceController {
    
    @Autowired
    CustomerServiceService customerServiceSvc;  
    @Autowired
    CounterOrderService counterOrderSvc;
    @Autowired
    NoticeService noticeSvc;
    @Autowired
    CounterService counterSvc;
    @Autowired
    CounterRepository counterRepository;
    @Autowired
    MsgService msgSvc;
    
    

    
    @GetMapping("/addComplaint")
    public String addComplaint(HttpSession session, ModelMap model) {
        Object memNoObj = session.getAttribute("memNo");
        if (memNoObj == null) {
            return "redirect:/mem/login";
        }

        CustomerServiceVO customerServiceVO = new CustomerServiceVO();
        List<CounterVO> counters = counterSvc.getAll();
        model.addAttribute("counters", counters);
        model.addAttribute("counterList", counterSvc.getAll());
        model.addAttribute("customerServiceVO", customerServiceVO);
        return "front-end/complaint/addcomplaint";
    }
    
    
    @PostMapping("/insert")
    public String insert(HttpSession session, 
    		@Valid CustomerServiceVO customerServiceVO,
    		BindingResult result, ModelMap model) {

        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
    	System.out.println(result.getErrorCount());
    	System.out.println(result.getFieldError("complaintReason"));
    	System.out.println(result.getFieldError("counterOrderNo"));
    	// 處理驗證錯誤
    	if (result.hasErrors()) {
    		model.addAttribute("counter", counterSvc.getOneCounter(customerServiceVO.getCounterNo()));
    		model.addAttribute("customerServiceSvc", customerServiceSvc);
    		model.addAttribute("counterList", counterRepository.findAll());
    		return "front-end/complaint/addcomplaint";
    	}
        // 設置表單提交的數據
    	
//        customerServiceVO.setComplaintReason(complaintReason); // 設置訊息內文
//        customerServiceVO.setCounterNo(counterNo); // 設置櫃位編號
//        customerServiceVO.setCounterOrderNo(counterOrderNo); // 設置櫃位訂單編號


        /*************************** 2.檢查會員登入狀態 ************************/
        Object memNoObj = session.getAttribute("memNo");
        if (memNoObj == null) {
            return "redirect:/mem/login";
        }

        Integer memNo = (memNoObj instanceof Integer) ? 
            (Integer) memNoObj : Integer.parseInt((String) memNoObj);

        // 設置會員編號
        customerServiceVO.setMemNo(memNo);

        /*************************** 3.新增資料 ************************/
        customerServiceSvc.addComplaint(customerServiceVO);

        // 新增完成，返回顯示所有客訴的視圖
        model.addAttribute("success", "新增成功");
        return "redirect:/member";  // 重定向到已登錄的會員頁面
    }

    
   


    
    

    
    

    

    
    
    @PostMapping("/submitReply")
    @ResponseBody
    public Map<String, Object> submitReply(@RequestParam("counterComplaintNo") Integer counterComplaintNo,
                                           @RequestParam("complaintReply") String complaintReply) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("Received counterComplaintNo: " + counterComplaintNo);
            System.out.println("Received complaintReply: " + complaintReply);

            CustomerServiceVO customerServiceVO = customerServiceSvc.getOneComplaint(counterComplaintNo);
            if (customerServiceVO == null) {
                response.put("success", false);
                response.put("error", "客訴不存在");
                return response;
            }

            System.out.println("Complaint before reply: " + customerServiceVO);

            // 保存回覆到 Notice 表
            NoticeVO noticeVO = new NoticeVO();
            noticeVO.setNoticeContent("訂單編號: " + customerServiceVO.getCounterOrderNo() + "，客訴回覆: " + complaintReply);
            noticeVO.setNoticeDate(new Timestamp(System.currentTimeMillis()));
            noticeVO.setMemNo(customerServiceVO.getMemNo()); // 使用 customerServiceVO 裡的 memNo

            noticeSvc.save(noticeVO);

            // 更新客訴狀態為處理完畢
            customerServiceVO.setComplaintStatus((byte) 1); // 1 表示處理完畢
            customerServiceSvc.updateComplaint(customerServiceVO);

            response.put("success", true);
            System.out.println("Reply submitted and status updated successfully");
        } catch (Exception e) {
            e.printStackTrace(); // 這樣可以在日誌中查看具體的異常堆棧信息
            response.put("success", false);
            response.put("error", "發生錯誤: " + e.getMessage());
        }
        return response;
    }


    
    @GetMapping("listCounterComplaint")
    public String listAllCustomerService(HttpSession session, Model model) {
        CounterVO counter = (CounterVO) session.getAttribute("counter");
        if (counter == null) {
            return "redirect:/counter/login";
        } else {
            List<CustomerServiceVO> list = customerServiceSvc.getOneCounterCustomerService(counter.getCounterNo());

            model.addAttribute("counter", counterSvc.getOneCounter(counter.getCounterNo()));
            model.addAttribute("counterCustomerServiceListData", list);
            model.addAttribute("customerServiceSvc", customerServiceSvc);
            model.addAttribute("msgSvc", msgSvc);
            return "vendor-end/customerService/listAllComplaint";
        }
    }



}




   



    

    
    
    
    
   
    



    



    


   

   
    
        
    


    



	
	
	



