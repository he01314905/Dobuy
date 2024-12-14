package com.counter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.counter.model.CounterService;
import com.counter.model.CounterVO;
import com.counterType.model.CounterTypeService;
import com.counterType.model.CounterTypeVO;
import com.msg.model.MsgService;

import java.util.*;

@Controller //ch2 P65 ch3 P77 ch8 p139
@RequestMapping("/back-end")
public class BackendCounterController {
	
	@Autowired
	CounterService counterSvc;
	
	@Autowired
	CounterTypeService counterTyperSvc;
	
    @Autowired
    MsgService msgSvc;
    
    @Autowired
    @Qualifier("primaryMailSender")
    private JavaMailSender mailSender;
    
    private void sendEmail(CounterVO counter) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(counter.getCounterEmail());
        message.setSubject("您的櫃位已被停權");
        message.setText("親愛的 " + counter.getCounterName() + "，\n\n" +
                        "我們很遺憾地通知您，您的櫃位 " + counter.getCounterCName() + " 已被停權。如有相關問題請盡速連繫客服。\n\n" +
                        "感謝您的理解。\n" +
                        "您的客服團隊");

        try {
            mailSender.send(message);
        } catch (MailException e) {
            // 處理發送郵件失敗的情況
            e.printStackTrace();
            // 這裡可以選擇記錄錯誤日志或拋出異常，但不要讓發送郵件的錯誤影響主業務邏輯
        }
    }
	
	@GetMapping("counter/test") //ch2 P65 ch3 P77 ch8 p139
	public String myMethod() {
		return "vendor-end/counter/index1"; //檔案位置 src\main\resources\templates\index1.html  //P137  
	}
	
	@GetMapping("counter/index2") //ch2 P65 ch3 P77 ch8 p139
	public String myMethod2() {
		return "vendor-end/counter/index2"; //檔案位置 src\main\resources\templates\index2.html  //P137  
	}
	
	@GetMapping("counter/index3") //ch2 P65 ch3 P77 ch8 p139
	@ResponseBody
	public String myMethod3() {
		return "<font color=green><b> index3 ?????   <b></font>";   
	}
	
	@GetMapping("/counter") //ch2 P65 ch3 P77 ch8 p139
	public String counterlist(Model model) {
		List<CounterVO> list = counterSvc.getAll();
		model.addAttribute("counterlist",list);
		return "vendor-end/counter/allcounter"; //檔案位置 src\main\resources\templates\counter\allcounter  //P137  
	}
	
	@PostMapping("/updateCStatus")
	public String updateCounterStatus(@RequestParam("counterStatus") Integer counterStatus,
	                                  @RequestParam("counterNo") Integer counterNo,
	                                  RedirectAttributes redirectAttributes) {
	    try {
	    	counterSvc.upStatus(counterNo, counterStatus);
	    	CounterVO counterVO = counterSvc.getOneCounter(Integer.valueOf(counterNo));
	        
	        redirectAttributes.addFlashAttribute("message", "櫃位狀態修改成功！");
	        //櫃位停權
            if (counterStatus == 0) {
                sendEmail(counterVO);
            }else if (counterStatus == 2) { 
	        	String informMsg = counterVO.getCounterCName() + " 您的櫃位狀態有違規行為，如有疑問請聯繫客服";
	            Integer counterNo1 = counterVO.getCounterNo();
	            msgSvc.addCounterInform(counterNo1, informMsg); // 新增通知
	        }else if (counterStatus == 3) {
	        	String informMsg = counterVO.getCounterCName() + " 您現在可以正常使用所有功能，祝您生意欣隆";
	            Integer counterNo1 = counterVO.getCounterNo();
	            msgSvc.addCounterInform(counterNo1, informMsg); // 新增通知
	        }
	    } catch (Exception e) {
	        // 這裡處理異常，例如記錄錯誤或添加錯誤消息
	        redirectAttributes.addFlashAttribute("error", "修改失敗：" + e.getMessage());
	    }
	    return "redirect:/back-end/counter"; 
	}
	
	   @ModelAttribute("counterListData")  // for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
		protected List<CounterVO> referenceListData(Model model) {
	    	List<CounterVO> list = counterSvc.getAll();
			return list;
		}
	   
	   @ModelAttribute("counterTypeListData") // for select_page.html 第135行用
	   protected List<CounterTypeVO> referenceListData_CounterType(Model model) {
	       model.addAttribute("counterTypeVO", new CounterTypeVO()); // for select_page.html 第133行用
	       List<CounterTypeVO> list = counterTyperSvc.getAll(); // 假設 counterTypeSvc 是處理 CounterType 的服務
	       return list;
	   }

}
