package com.usedecpay.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usedorder.model.UsedOrderService;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutOneTime;
import ecpay.payment.integration.ecpayOperator.EcpayFunction;

@Service
public class UsedEcpayService {
	
	@Autowired
	UsedOrderService usedOrderService;
	
	@Transactional
	public static String genAioCheckOutOneTime(String usedNo,
											   String usedName,
											   String usedCount,
											   Timestamp now,
											   String totalAmount,
											   Integer usedOrderNo){
		try {
			
		String orderIdOpening = usedOrderNo + "Dobuy"; 
		String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, (20 - orderIdOpening.length()));
		String fullOrderId = orderIdOpening + uuId;
			
		
		AllInOne all = new AllInOne("");
		
		AioCheckOutOneTime obj = new AioCheckOutOneTime();
		
		//獲取當前交易時間 "2017/01/01 08:05:23"
	
		obj.setMerchantTradeNo(fullOrderId);        //v
		obj.setMerchantTradeDate(formatTimestamp(now));
		obj.setTotalAmount(totalAmount);
		obj.setTradeDesc("商品名稱 "+usedName +"商品件數"+usedCount+"件");
		obj.setItemName("訂單編號"+usedOrderNo+"商品編號 "+usedNo+"商品名稱 "+usedName +"商品件數"+usedCount+"件");
		obj.setReturnURL("https://dc5b-111-243-143-5.ngrok-free.app/used/ecpay/notify");// 接收綠界收款回覆的controller網址，controller做完資料分析及資料儲存後，回覆1|OK給綠界
//		obj.setReturnURL("https://d0e9-111-249-22-132.ngrok-free.app/used/ecpay/notify");// 接收綠界收款回覆的controller網址，controller做完資料分析及資料儲存後，回覆1|OK給綠界

//		obj.setOrderResultURL("http://localhost:8080/used/select_page");
		obj.setNeedExtraPaidInfo("N");
		obj.setRedeem("N");
		obj.setClientBackURL("http://localhost:8080/member");// 設定付款完成後消費者要看到的網頁(轉網址)
		obj.setCustomField1(usedOrderNo.toString());
		String form = all.aioCheckOut(obj, null);
	
		
		return form;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	
	public static String formatTimestamp(Timestamp timestamp) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime().withNano(0); // 去掉毫秒
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
}
