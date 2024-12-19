package com.usedorder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import com.usedorder.model.UsedOrderVO;
import com.notice.model.NoticeVO;
import com.used.model.UsedVO;
import com.usedorder.model.UsedOrderService;
import com.notice.model.NoticeService;


@Controller
@RequestMapping("/usedorder")
public class UsedOrderController {

	@Autowired
	UsedOrderService usedOrderSvc;
	@Autowired
	NoticeService noticeSvc;
	
	
	@GetMapping("listAllUsedOrder")
	public String getAllOrder(ModelMap model) {
	    List<UsedOrderVO> list = usedOrderSvc.getAll(); 
	    // 過濾掉 deliveryStatus = 6 的訂單
	    List<UsedOrderVO> filteredList = list.stream()
	                                         .filter(order -> order.getDeliveryStatus() != 6)
	                                         .collect(Collectors.toList());
	    model.addAttribute("usedorderListData", filteredList);
	    return "front-end/usedorder/listAllUsedOrder";
	}


	// 買家訂單管理
	@GetMapping("listAllUsedOrder2")
	public String getAllOrder2(ModelMap model) {
	    List<UsedOrderVO> list = usedOrderSvc.getAll();
	 // 過濾掉 deliveryStatus = 6 的訂單
	    List<UsedOrderVO> filteredList = list.stream()
	                                         .filter(order -> order.getDeliveryStatus() != 6)
	                                         .collect(Collectors.toList());
	    model.addAttribute("usedorderListData", filteredList);
	    return "front-end/usedorder/listAllUsedOrder2";
	}

   
	
    

	

	
}





    