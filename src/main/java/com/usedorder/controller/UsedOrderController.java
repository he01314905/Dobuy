package com.usedorder.controller;

import javax.servlet.http.HttpServletRequest;
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

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import com.usedorder.model.UsedOrderVO;
import com.notice.model.NoticeVO;
import com.usedorder.model.UsedOrderService;
import com.notice.model.NoticeService;


@Controller
@RequestMapping("/usedorder")
public class UsedOrderController {

	@Autowired
	UsedOrderService usedOrderSvc;
	@Autowired
	NoticeService noticeSvc;
	
	@PostMapping("updateDeliveryStatus")
	@ResponseBody
	public Map<String, Object> updateDeliveryStatus(@RequestParam("usedOrderNo") Integer usedOrderNo,
	                                                @RequestParam("deliveryStatus") Byte deliveryStatus) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        System.out.println("Received usedOrderNo: " + usedOrderNo);
	        System.out.println("Received deliveryStatus: " + deliveryStatus);

	        UsedOrderVO usedOrderVO = usedOrderSvc.getOneUsedOrder(usedOrderNo);
	        if (usedOrderVO == null) {
	            response.put("success", false);
	            response.put("error", "訂單不存在");
	            return response;
	        }

	        System.out.println("Order before update: " + usedOrderVO);

	        usedOrderVO.setDeliveryStatus(deliveryStatus);
	        usedOrderSvc.updateUsedOrder(usedOrderVO);

	        // 发送通知消息
	        String deliveryStatusText = getDeliveryStatusText(deliveryStatus);
	        NoticeVO noticeVO = new NoticeVO();
	        noticeVO.setNoticeContent("訂單號 " + usedOrderNo + " 宅配狀態更新為" + deliveryStatusText);
	        noticeVO.setNoticeDate(new Timestamp(System.currentTimeMillis()));
	        noticeSvc.save(noticeVO);

	        response.put("success", true);
	        System.out.println("Update successful");
	        return response;
	    } catch (Exception e) {
	        e.printStackTrace(); // 這樣可以在日誌中查看具體的異常堆棧信息
	        response.put("success", false);
	        response.put("error", "發生錯誤: " + e.getMessage());
	        return response;
	    }
	}
	private String getDeliveryStatusText(Byte deliveryStatus) {
		switch (deliveryStatus) {
		case 0: return "未出貨"; 
		case 1: return "已出貨"; 
		case 2: return "待領件"; 
		case 3: return "已領貨"; 
		case 4: return "已取消"; 
		default: return "未知狀態"; } }

	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(UsedOrderVO usedOrderVO, BindingResult result, String removedFieldname) {
	    List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
	            .filter(fieldname -> !fieldname.getField().equals(removedFieldname))
	            .collect(Collectors.toList());
	    result = new BeanPropertyBindingResult(usedOrderVO, "usedOrderVO");
	    for (FieldError fieldError : errorsListToKeep) {
	        result.addError(fieldError);
	    }
	    return result;
	}

	// 賣家訂單管理
	@GetMapping("listAllUsedOrder")
	public String getAllOrder(ModelMap model) {
	    List<UsedOrderVO> list = usedOrderSvc.getAll(); 
	    model.addAttribute("usedorderListData", list);
	    return "front-end/usedorder/listAllUsedOrder";
	}

	// 買家訂單管理
	@GetMapping("listAllUsedOrder2")
	public String getAllOrder2(ModelMap model) {
	    List<UsedOrderVO> list = usedOrderSvc.getAll();
	    model.addAttribute("usedorderListData", list);
	    return "front-end/usedorder/listAllUsedOrder2";
	}

    @PostMapping("updateComment")
    @ResponseBody
    public Map<String, Object> updateComment(@RequestParam("usedOrderNo") Integer usedOrderNo,
                                             @RequestParam("sellerCommentContent") String sellerCommentContent,
                                             @RequestParam("sellerSatisfication") byte sellerSatisfication) {
        Map<String, Object> response = new HashMap<>();
        try {
            UsedOrderVO usedOrder = usedOrderSvc.getOrderById(usedOrderNo);

            if (usedOrder != null) {
                usedOrder.setSellerCommentContent(sellerCommentContent);
                usedOrder.setSellerSatisfication(sellerSatisfication);
                System.out.println("===================================");
                System.out.println(sellerCommentContent);
                
                System.out.println(sellerSatisfication);
                System.out.println("===================================");
                usedOrderSvc.updateOrder(usedOrder);

                response.put("success", true);
            } else {
                response.put("success", false);
                response.put("error", "訂單未找到");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return response;
    }
	
}





    