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
import com.goods.model.GoodsService;
import com.goods.model.GoodsVO;
import com.memcoupon.model.MemCouponService;
import com.memcoupon.model.MemCouponVO;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
//@RequestMapping("/checkout")
public class CheckoutCouponController {

@Autowired
ShoppingCartListService shoppingCartListService;

@Autowired
CouponService couponService;

@Autowired
CouponDetailService couponDetailService;

@Autowired
MemCouponService memCouponService;

@Autowired
CounterOrderService counterOrderService;    

@Autowired
CounterOrderDetailService counterOrderDetailService;


@Autowired
GoodsService goodsService;



//購物車預覽優惠顯示結帳頁面============顯示結帳頁面
@GetMapping("/shoppingcartlist/ShoppingCartListCheckout49")
public String showCheckoutPage(HttpSession session, Model model) {
Object memNoObj = session.getAttribute("memNo");
//        Integer memNo = (Integer) session.getAttribute("memNo");
//        model.addAttribute("memNo",memNo);
try {
if (memNoObj == null) {
return "redirect:/mem/login";
}

Integer memNo = (memNoObj instanceof Integer) ? (Integer) memNoObj : 
Integer.parseInt((String) memNoObj);

// 獲取購物車商品
List<ShoppingCartListVO> cartItems = shoppingCartListService.getCartItemsByMemNo49(memNo);

// 按櫃位分組商品
Map<Integer, List<ShoppingCartListVO>> cartItemsByCounter = new HashMap<>();

// 為每個購物車項目獲取對應的商品資訊並分組
for (ShoppingCartListVO cartItem : cartItems) {
GoodsVO goods = goodsService.getOneGoods(cartItem.getGoodsNo());
List<GoodsVO> insufficientStockItems = new ArrayList<>();
if (goods != null && goods.getCounterVO() != null) {
Integer counterNo = goods.getCounterVO().getCounterNo(); // 從 CounterVO 取得櫃位編號
cartItemsByCounter
.computeIfAbsent(counterNo, k -> new ArrayList<>())
.add(cartItem);
model.addAttribute("counterNo",counterNo);
}
if (goods.getGoodsAmount() < cartItem.getGoodsNum()) {
insufficientStockItems.add(goods);
} else {
// 扣除庫存
goods.setGoodsAmount(goods.getGoodsAmount() - cartItem.getGoodsNum());
goodsService.updateGoodsAmount(cartItem.getGoodsNo(), goods.getGoodsAmount());

}
if (!insufficientStockItems.isEmpty()) {
for(GoodsVO a:insufficientStockItems) {
model.addAttribute("error", "以下商品庫存不足："+a.getGoodsName());

}
model.addAttribute("shoppingCartListListData",cartItems);
return "front-end/shoppingcartlist/listAllShoppingCartList";
}
session.setAttribute("cartItems", cartItems);

model.addAttribute("carlist", cartItems);
}

// 計算每個櫃位的小計
Map<Integer, Integer> counterTotals = new HashMap<>();
for (Map.Entry<Integer, List<ShoppingCartListVO>> entry : cartItemsByCounter.entrySet()) {
int counterTotal = entry.getValue().stream()
.mapToInt(item -> item.getGoodsPrice() * item.getGoodsNum())
.sum();
counterTotals.put(entry.getKey(), counterTotal);
}

// 計算總金額
int totalAmount = counterTotals.values().stream()
.mapToInt(Integer::intValue)
.sum();

// 獲取會員可用的優惠券
List<MemCouponVO> availableCoupons = memCouponService.getAvailableCoupons(memNo);

model.addAttribute("cartItemsByCounter", cartItemsByCounter);
model.addAttribute("counterTotals", counterTotals);
model.addAttribute("totalAmount", totalAmount);
model.addAttribute("availableCoupons", availableCoupons);

return "front-end/shoppingcartlist/ShoppingCartListCheckout49";

} catch (Exception e) {
e.printStackTrace();
model.addAttribute("errorMessage", "載入結帳頁面時發生錯誤");
return "error";
}
}


// 計算折扣金額================計算優惠折扣預覽
@PostMapping("/shoppingcartlist/calculateDiscount")
@ResponseBody
public Map<String, Object> calculateDiscount(
@RequestParam("couponNo") String couponNoStr,
@RequestParam("counterId") String counterIdStr,
HttpSession session) {
try {
// session 處理
Object memNoObj = session.getAttribute("memNo");
Integer memNo;
if (memNoObj instanceof Integer) {
memNo = (Integer) memNoObj;
} else if (memNoObj instanceof String) {
memNo = Integer.parseInt((String) memNoObj);
} else if (memNoObj == null) {
return Map.of("error", "請先登入");
} else {
return Map.of("error", "會員資訊錯誤");
}

Integer couponNo = Integer.parseInt(couponNoStr);
Integer counterId = Integer.parseInt(counterIdStr);

// 獲取優惠券資訊
CouponVO coupon = couponService.getOneCouponWithDetails(couponNo);
if (coupon == null) {
return Map.of("error", "優惠券不存在");
}

// 獲取並過濾購物車商品
List<ShoppingCartListVO> cartItems = shoppingCartListService.getCartItemsByMemNo49(memNo)
.stream()
.filter(item -> {
GoodsVO goods = goodsService.getOneGoods(item.getGoodsNo());
return goods != null && 
goods.getCounterVO() != null && 
goods.getCounterVO().getCounterNo().equals(counterId);
})
.collect(Collectors.toList());

// 計算每個商品的總金額
Map<Integer, Double> goodsTotalMap = new HashMap<>();
double totalBeforeDiscount = 0;

for (ShoppingCartListVO item : cartItems) {
double itemTotal = item.getGoodsPrice() * item.getGoodsNum();
goodsTotalMap.put(item.getGoodsNo(), itemTotal);
totalBeforeDiscount += itemTotal;
System.out.println("商品 " + item.getGoodsNo() + " 總金額: " + itemTotal);
}

// 計算折扣
double totalDiscount = 0;
boolean hasAnyDiscount = false;

for (ShoppingCartListVO item : cartItems) {
// 檢查商品是否有對應的優惠券明細
for (CouponDetailVO detail : coupon.getCouponDetails()) {
if (detail.getGoodsNo().equals(item.getGoodsNo())) {
double threshold = Double.parseDouble(detail.getCounterContext());
double goodsTotal = goodsTotalMap.get(item.getGoodsNo());

System.out.println("商品 " + item.getGoodsNo() + 
" 門檻: " + threshold + 
" 實際金額: " + goodsTotal);

if (goodsTotal >= threshold) {
double discountRate = detail.getDisRate();
double itemDiscount = goodsTotal * (1 - discountRate);
totalDiscount += itemDiscount;
hasAnyDiscount = true;

System.out.println("商品 " + item.getGoodsNo() + 
" 達到門檻，折扣金額: " + itemDiscount);
}
break;
}
}
}

// 如果沒有任何商品符合優惠條件
if (!hasAnyDiscount) {
return Map.of(
"error", "無符合優惠條件的商品",
"discount", 0,
"newTotal", totalBeforeDiscount
);
}

// 計算最終金額
double newTotal = Math.max(0, totalBeforeDiscount - totalDiscount);

System.out.println("計算結果:");
System.out.println("原始金額: " + totalBeforeDiscount);
System.out.println("折扣金額: " + totalDiscount);
System.out.println("最終金額: " + newTotal);

Map<String, Object> result = new HashMap<>();
result.put("discount", totalDiscount);
result.put("newTotal", newTotal);
result.put("description", coupon.getCouponContext() != null ? 
coupon.getCouponContext() : "");
result.put("originalTotal", totalBeforeDiscount);

return result;

} catch (Exception e) {
e.printStackTrace();
return Map.of("error", "計算折扣時發生錯誤: " + e.getMessage());
}
}


}
