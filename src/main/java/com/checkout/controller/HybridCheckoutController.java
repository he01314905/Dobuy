package com.checkout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ShoppingCartList.model.ShoppingCartListService;
import com.ShoppingCartList.model.ShoppingCartListVO;
import com.counter.model.CounterVO;
import com.counterorder.model.CounterOrderService;
import com.counterorder.model.CounterOrderVO;
import com.counterorderdetail.model.CounterOrderDetailService;
import com.counterorderdetail.model.CounterOrderDetailVO;
import com.coupon.model.CouponService;
import com.coupon.model.CouponVO;
import com.coupondetail.model.CouponDetailService;
import com.coupondetail.model.CouponDetailVO;
import com.ecpay.demo.service.OrderService;
import com.goods.model.GoodsService;
import com.goods.model.GoodsVO;
import com.memcoupon.model.MemCouponService;
import com.memcoupon.model.MemCouponVO;

import ecpay.payment.integration.domain.AioCheckOutALL;
import ecpay.payment.integration.domain.AioCheckOutOneTime;

import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class HybridCheckoutController {
    @Autowired
    private ShoppingCartListService shoppingCartListService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponDetailService couponDetailService;
    @Autowired
    private MemCouponService memCouponService;
    @Autowired
    private CounterOrderService counterOrderService;
    @Autowired
    private CounterOrderDetailService counterOrderDetailService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    @ResponseBody
    @Transactional
    public String processCheckout(@RequestBody CheckoutRequest checkoutRequest, HttpSession session) {
        try {
            // 1. 驗證會員權限
            Integer memNo = validateMember(session);
            if (memNo == null) {
                return "請先登入";
            }

            // 2. 獲取購物車內容
            @SuppressWarnings("unchecked")
            List<ShoppingCartListVO> cartItems = (List<ShoppingCartListVO>) session.getAttribute("cartItems");
            if (cartItems == null || cartItems.isEmpty()) {
                return "購物車是空的";
            }

            // 3. 創建訂單和訂單明細
            CounterOrderVO order = createOrder(checkoutRequest, memNo, cartItems);
            if (order == null) {
                return "創建訂單失敗";
            }

            // 4. 處理優惠券折扣
            processDiscounts(checkoutRequest.getCounterCoupons(), order, memNo);

            // 5. 生成綠界支付表單
            List<String> itemNames = getItemNames(cartItems);
            String aioCheckOutALLForm = orderService.generateEcpayNum(
                order.getOrderTotalAfter(),
                
                itemNames,
                order.getCounterOrderNo()
            );

            // 6. 清空購物車
            // 確保清空購物車在表單生成之後
            session.removeAttribute("cartItems");
            
            // 添加驗證
            if (order.getOrderTotalAfter() <= 0) {
                return "訂單金額必須大於0";
            }

            // 添加日誌
            System.out.println("處理訂單，總金額：" + order.getOrderTotalAfter());
            System.out.println("訂單編號：" + order.getCounterOrderNo());

            List<String> itemNames1 = getItemNames(cartItems);
            String aioCheckOutALLForm1 = orderService.generateEcpayNum(
                order.getOrderTotalAfter(),
                itemNames1,
                order.getCounterOrderNo()
            );

            session.removeAttribute("cartItems");
            return aioCheckOutALLForm1;
            
        } catch (Exception e) {
            System.err.println("結帳處理發生錯誤：" + e.getMessage());
            e.printStackTrace();
            return "付款過程中發生錯誤：" + e.getMessage();
        }
    }

    
    private Integer validateMember(HttpSession session) {
        Object memNoObj = session.getAttribute("memNo");
        try {
            if (memNoObj instanceof Integer) {
                return (Integer) memNoObj;
            } else if (memNoObj instanceof String) {
                return Integer.parseInt((String) memNoObj);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return null;
    }

    private CounterOrderVO createOrder(CheckoutRequest request, Integer memNo, List<ShoppingCartListVO> cartItems) {
        try {
            int totalPrice = cartItems.stream()
                .mapToInt(item -> item.getGoodsNum() * item.getGoodsPrice())
                .sum();

            CounterOrderVO order = new CounterOrderVO();
            order.setReceiverAdr(request.getAddress());
            order.setOrderTotalBefore(totalPrice);
            order.setOrderTotalAfter(totalPrice); // 初始值，可能會被優惠券修改
            order.setReceiverName(request.getName());
            order.setReceiverPhone(request.getPhone());
            for(ShoppingCartListVO a: cartItems) {
            	order.setCounterNo(a.getCounterNo());
            }

            order.setMemNo(memNo);
            order.setOrderStatus(5);

            counterOrderService.addCounterOrder(order);

            // 創建訂單明細
            createOrderDetails(cartItems, order.getCounterOrderNo());

            return order;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createOrderDetails(List<ShoppingCartListVO> cartItems, Integer counterOrderNo) {
        cartItems.forEach(cartItem -> {
        	
        		GoodsVO goods=goodsService.getOneGoods(cartItem.getGoodsNo());
                goods.setGoodsAmount(goods.getGoodsAmount() - cartItem.getGoodsNum());
                goodsService.updateGoodsAmount(cartItem.getGoodsNo(), goods.getGoodsAmount());
            
           
            CounterOrderDetailVO detail = new CounterOrderDetailVO();
            detail.setGoodsNo(cartItem.getGoodsNo());
            detail.setGoodsNum(cartItem.getGoodsNum());
            detail.setProductPrice(cartItem.getGoodsPrice());
            detail.setProductDisPrice(cartItem.getOrderTotalprice());
            detail.setCounterOrderNo(counterOrderNo);
            counterOrderDetailService.addCounterOrderDetail(detail);
        });
    }

    private void processDiscounts(Map<Integer, Integer> counterCoupons, CounterOrderVO order, Integer memNo) {
        if (counterCoupons == null || counterCoupons.isEmpty()) {
            return;
        }

        counterCoupons.forEach((counterNo, couponNo) -> {
            if (couponNo != null) {
                MemCouponVO memCoupon = memCouponService.getOneMemCoupon(couponNo);
                if (memCoupon != null && memCoupon.getStatus() != 1) {
                    applyDiscount(order, memCoupon);
                }
            }
        });
    }

    private void applyDiscount(CounterOrderVO order, MemCouponVO memCoupon) {
        try {
            CouponVO coupon = memCoupon.getCoupon();
            List<CounterOrderDetailVO> orderDetails = counterOrderDetailService.getDetailsByOrderNo(order.getCounterOrderNo());
            
            // 建立優惠券商品映射
            Map<Integer, CouponDetailVO> couponDetailMap = new HashMap<>();
            coupon.getCouponDetails().forEach(detail -> 
                couponDetailMap.put(detail.getGoodsNo(), detail));

            // 處理每個明細的折扣
            for (CounterOrderDetailVO detail : orderDetails) {
                applySingleItemDiscount(detail, couponDetailMap, memCoupon);
            }

            // 更新訂單總金額
            updateOrderTotal(order, orderDetails, memCoupon);

            // 更新優惠券狀態
            memCoupon.setStatus(1);
            memCouponService.updateMemCoupon(memCoupon);

        } catch (Exception e) {
            throw new RuntimeException("處理折扣時發生錯誤", e);
        }
    }

    private void applySingleItemDiscount(CounterOrderDetailVO detail, 
            Map<Integer, CouponDetailVO> couponDetailMap, 
            MemCouponVO memCoupon) {
        
        CouponDetailVO couponDetail = couponDetailMap.get(detail.getGoodsNo());
        if (couponDetail != null) {
            double threshold = Double.parseDouble(couponDetail.getCounterContext());
            double itemTotal = detail.getProductPrice() * detail.getGoodsNum();

            if (itemTotal >= threshold) {
                double discountRate = couponDetail.getDisRate();
                int discountedPrice = (int) Math.round(detail.getProductPrice() * discountRate);
                detail.setProductDisPrice(discountedPrice);
                detail.setMemCouponNo(memCoupon.getMemCouponNo());
                counterOrderDetailService.updateCounterOrderDetail(detail);
            }
        }
    }

    private void updateOrderTotal(CounterOrderVO order, 
            List<CounterOrderDetailVO> orderDetails,
            MemCouponVO memCoupon) {
        
        int finalTotal = orderDetails.stream()
            .mapToInt(detail -> detail.getProductDisPrice() * detail.getGoodsNum())
            .sum();

        order.setOrderTotalAfter(finalTotal);
//        order.setMemCouponNo(memCoupon.getMemCouponNo());
        counterOrderService.updateCounterOrder49(order);
    }

    private List<String> getItemNames(List<ShoppingCartListVO> cartItems) {
        return cartItems.stream()
            .map(item -> item.getGoodsName() + " x" + item.getGoodsNum())
            .collect(Collectors.toList());
    }
    
    // 處理綠界支付完成後的回調
    @PostMapping("/member")
    public String handleECPayCallback(@RequestParam Map<String, String> allParams) {
        try {
            // 記錄接收到的參數
            System.out.println("接收到綠界回調，參數：" + allParams);
            
            // 取得交易狀態
            String status = allParams.get("RtnCode");
            
            // 根據交易狀態更新訂單
            if ("1".equals(status)) {
                // 交易成功
                String merchantTradeNo = allParams.get("MerchantTradeNo");
                String paymentDate = allParams.get("PaymentDate");
                String counterOrderNo = allParams.get("CustomField1");                // TODO: 更新訂單狀態
                CounterOrderVO  counterOrderVO;
                counterOrderVO  = counterOrderService.getOneCounterOrder(Integer.valueOf(counterOrderNo));
                counterOrderVO.setOrderStatus(0);
                counterOrderService.updateCounterOrder(counterOrderVO);
                
                return "redirect:/member"; // 導向會員頁面
            } else {
                // 交易失敗
                return "redirect:/member?error=payment_failed";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/member?error=system_error";
        }
    }
    
    // 處理綠界通知（ReturnURL）
    @PostMapping("/ecpay/callback")
    @ResponseBody
    public String handleECPayNotification(@RequestParam Map<String, String> allParams) {
        try {
            // 記錄通知
            System.out.println("接收到綠界通知，參數：" + allParams);
            
            // 驗證參數
            String status = allParams.get("RtnCode");
            if ("1".equals(status)) {
                // 交易成功，更新訂單狀態
                String merchantTradeNo = allParams.get("MerchantTradeNo");
                // TODO: 更新訂單狀態
                
                return "1|OK";
            }
            
            return "0|Error";
        } catch (Exception e) {
            e.printStackTrace();
            return "0|Error";
        }
    }
    
    
}