package com.ecpay.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecpay.payment.integration.AllInOne;
import com.ecpay.payment.integration.domain.AioCheckOutALL;
import com.ecpay.payment.integration.domain.AioCheckOutOneTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    
    @Transactional
    public String generateEcpayNum(Integer total, List<String> itemNames, Integer counterOrderNo) {
        System.out.println("準備生成綠界訂單");
        System.out.println("訂單金額: " + total);
        System.out.println("商品名稱: " + itemNames);
        System.out.println("訂單編號: " + counterOrderNo);
        
        AllInOne all = new AllInOne("");

        try {
            // 確保total不為空且大於0
            if (total == null || total <= 0) {
                throw new IllegalArgumentException("訂單金額必須大於0");
            }
            
            // 確保itemNames不為空
            if (itemNames == null || itemNames.isEmpty()) {
                throw new IllegalArgumentException("商品名稱不能為空");
            }
            
            // 生成交易編號
            String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
            
            // 處理商品名稱
            String itemName = itemNames.stream()
                    .filter(name -> name != null && !name.trim().isEmpty())
                    .limit(3)  // 限制商品名稱數量
                    .map(name -> name.length() > 20 ? name.substring(0, 20) : name)  // 限制每個商品名稱長度
                    .reduce((a, b) -> a + "#" + b)
                    .orElse("DO BUY商品");  // 預設商品名稱
            
            // 建立購物參數
            AioCheckOutOneTime obj = new AioCheckOutOneTime();
            obj.setMerchantID("3002607");
            obj.setMerchantTradeNo(uuId);
            obj.setMerchantTradeDate(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            obj.setTotalAmount(String.valueOf(total));  // 確保轉換為字串
            obj.setTradeDesc("DO BUY商品訂單");
            obj.setItemName(itemName);
            obj.setCustomField1(String.valueOf(counterOrderNo));
//            obj.setCustomField1(String.valueOf(counterOrderNo));

            // 設定付款結果通知參數
            obj.setReturnURL("https://dc5b-111-243-143-5.ngrok-free.app/ecpay/callback");

            obj.setOrderResultURL("http://localhost:8080/member");
            obj.setClientBackURL("http://localhost:8080/member");
            obj.setNeedExtraPaidInfo("N");
            
            System.out.println("綠界參數設置完成，準備送出請求");
            
            // 產生綠界表單
            String form = all.aioCheckOut(obj, null);
            System.out.println("綠界表單生成成功");
            return form;
            
        } catch (Exception e) {
            System.err.println("生成綠界支付表單時發生錯誤: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("生成綠界支付表單時發生錯誤: " + e.getMessage());
        }
    }
}

