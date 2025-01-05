package com.usedorder.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.notice.model.NoticeService;
import com.notice.model.NoticeVO;
import com.used.model.UsedService;
import com.used.model.UsedVO;
import com.usedorder.model.UsedOrderService;
import com.usedorder.model.UsedOrderVO;


@Controller
@RequestMapping("/usedorder")
public class UsedOrderFragController {
	
	@Autowired
	UsedOrderService usedOrderSvc;
	
	@Autowired
	UsedService usedSvc;
	
	@Autowired
	NoticeService noticeSvc;
	
	//查詢身為賣家的訂單
	@PostMapping("/getSellerUsedOrderListFragment")
    public String getSellerUsedOrderListFragment(HttpSession session, Model model) {
        // 從 session 中取得 memNo
		if(session.getAttribute("memNo")==null) {
			return "front-end/usedorder/SellerUsedOrderFragment :: usedOrderFragment";
		}
		
        Integer memNo = Integer.valueOf((String)session.getAttribute("memNo"));    
        
     // 根據 memNo 從資料庫中查詢該位會員所販賣的二手商品
     		List<UsedVO> usedList = usedSvc.adminSelectBySellerNo(memNo);
     		if(usedList.isEmpty()) {
     			return "front-end/usedorder/SellerUsedOrderFragment :: usedOrderFragment";
     		}
     		//根據拿取到的usedList去搜尋該位賣家賣的商品所有訂單
     		//將商品編號提取出來放入新list
     		List<Integer> usedNoList = new ArrayList<>();
     		for(UsedVO usedVO:usedList) {
     			usedNoList.add(usedVO.getUsedNo());
     		}
     		//再將提取出來的商品編號List去搜尋該位賣家賣的商品所有訂單
     		List<UsedOrderVO> filteredList = usedOrderSvc.selectSellerOrderBySellerUsedNo(usedNoList);
     
        // 將數據放到模型中
        model.addAttribute("usedorderListData", filteredList);
        // 返回Fragment所在的模板，並指定Fragment名稱
        return "front-end/usedorder/SellerUsedOrderFragment :: usedOrderFragment";
    }
	
	
	//查詢身為買家的訂單
	@PostMapping("/getBuyerUsedOrderListFragment")
	public String getBuyerUsedOrderFragment(HttpSession session, Model model) {
		// 從 session 中取得 memNo
		if(session.getAttribute("memNo")==null) {
			return "front-end/usedorder/BuyerUsedOrderFragment :: usedOrderFragment";
		}
		Integer memNo = Integer.valueOf((String)session.getAttribute("memNo"));
		
		 List<UsedOrderVO> filteredList = usedOrderSvc.selectBuyerOrderByMemNo(memNo);
		 
		model.addAttribute("usedorderListData", filteredList);
		// 返回Fragment所在的模板，並指定Fragment名稱
		return "front-end/usedorder/BuyerUsedOrderFragment :: usedOrderFragment";
	}

	
	
	
	
//================================================================	
	
	
	//查詢身為賣家的訂單
		@PostMapping("/getSellerUsedOrderListFragmenttest")
	    public String getSellerUsedOrderListFragmenttest(HttpSession session, Model model) {
	        // 從 session 中取得 memNo
			if(session.getAttribute("memNo")==null) {
				return "front-end/usedorder/SellerUsedOrderFragment";
			}
			
	        Integer memNo = Integer.valueOf((String)session.getAttribute("memNo"));    
	        
	     // 根據 memNo 從資料庫中查詢該位會員所販賣的二手商品
	     		List<UsedVO> usedList = usedSvc.adminSelectBySellerNo(memNo);
	     		if(usedList.isEmpty()) {
	     			return "front-end/usedorder/SellerUsedOrderFragment";
	     		}
	     		//根據拿取到的usedList去搜尋該位賣家賣的商品所有訂單
	     		//將商品編號提取出來放入新list
	     		List<Integer> usedNoList = new ArrayList<>();
	     		for(UsedVO usedVO:usedList) {
	     			usedNoList.add(usedVO.getUsedNo());
	     		}
	     		//再將提取出來的商品編號List去搜尋該位賣家賣的商品所有訂單
	     		List<UsedOrderVO> filteredList = usedOrderSvc.selectSellerOrderBySellerUsedNo(usedNoList);
	     
	        // 將數據放到模型中
	        model.addAttribute("usedorderListData", filteredList);
	        // 返回Fragment所在的模板，並指定Fragment名稱
	        return "front-end/usedorder/SellerUsedOrderFragment";
	    }
		
		
		//查詢身為買家的訂單
		@PostMapping("/getBuyerUsedOrderListFragmenttest")
		public String getBuyerUsedOrderFragmenttest(HttpSession session, Model model) {
			// 從 session 中取得 memNo
			if(session.getAttribute("memNo")==null) {
				return "front-end/usedorder/BuyerUsedOrderFragment";
			}
			Integer memNo = Integer.valueOf((String)session.getAttribute("memNo"));
			
			 List<UsedOrderVO> filteredList = usedOrderSvc.selectBuyerOrderByMemNo(memNo);
			// 將數據放到模型中
			model.addAttribute("usedorderListData", filteredList);
			// 返回Fragment所在的模板，並指定Fragment名稱
			return "front-end/usedorder/BuyerUsedOrderFragment";
		}
		
		
		

	
		
		@PostMapping("/updateFragComment")
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
		            // 假設您有一些邏輯來返回更新後的圖片 URL
		            response.put("imageUrl", "path/to/updated/image.jpg"); // 這裡填寫更新後的圖片 URL
		        } else {
		            response.put("success", false);
		            response.put("error", "訂單未找到");
		        }
		    } catch (Exception e) {
		    	e.printStackTrace();
		        response.put("success", false);
		        response.put("error", e.getMessage());
		    }

		    return response;
		}
		
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

		        // 發送通知消息
		        String deliveryStatusText = getDeliveryStatusText(deliveryStatus);
		        NoticeVO noticeVO = new NoticeVO();
		        noticeVO.setNoticeContent("訂單號 " + usedOrderNo + " 訂單狀態更新為" + deliveryStatusText);
		        noticeVO.setNoticeDate(new Timestamp(System.currentTimeMillis()));
		        
		        // 獲取 buyerNo 並設置到 noticeVO 的 memNo
		        Integer buyerNo = usedOrderVO.getBuyerNo();
		        noticeVO.setMemNo(buyerNo);

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
			case 5: return "已付款";
			case 6: return "未付款";
			default: return "已付款"; } }

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
		
		@PostMapping("sendComplaintEmail")
	    @ResponseBody
	    public Map<String, Object> sendComplaintEmail(@RequestParam("orderNo") Integer orderNo,
	                                                  @RequestParam("subject") String subject,
	                                                  @RequestParam("content") String content) {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            // 發送郵件的邏輯
	            // 這裡你可以使用之前分享的 JavaMail API 代碼來發送郵件

	            // 假設這是你的發送郵件邏輯
	            MailSender.sendEmail("cia103g2.dobuy@gmail.com", subject, content);

	            response.put("success", true);
	        } catch (Exception e) {
	            response.put("success", false);
	            response.put("error", "發生錯誤: " + e.getMessage());
	        }
	        return response;
	    }


	
	
}
