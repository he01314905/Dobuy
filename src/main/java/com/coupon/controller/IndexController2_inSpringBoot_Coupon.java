package com.coupon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.counter.model.CounterService;
import com.counter.model.CounterVO;
import com.coupon.model.CouponService;
import com.coupon.model.CouponVO;
import com.goods.model.GoodsVO;

import java.util.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/coupon")
public class IndexController2_inSpringBoot_Coupon {
    
    // 自动装配 CouponService
    @Autowired
    CouponService couponSvc;
    
    @Autowired
	CounterService counterSvc;



    // 主页映射
//     @GetMapping("/inindex")
//     public String index(Model model) {
//         return "vendor-end/coupon/inindex"; // 对应视图名称
//     }


    // 提供查询页面（绝对路径）
//     @GetMapping("/select_page")
//     public String selectPage(Model model) {
//         return "vendor-end/coupon/select_page";
//     }

    // 提供所有优惠券列表页面
     @GetMapping("/listAllCoupon")
     public String listAllCoupon(HttpSession session, Model model) {
     	//櫃位優惠券登錄確認
         CounterVO counter = (CounterVO) session.getAttribute("counter");
         if (counter == null) {
             return "redirect:/counter/login";
         } else {
             // 其他邏輯
             model.addAttribute("counter", counterSvc.getOneCounter(counter.getCounterNo()));
             model.addAttribute("counterCouponListData", couponSvc.getOneCounter46(counter.getCounterNo()));
             return "vendor-end/coupon/listAllCoupon";
         }
     }
    

	// 提供CouponVO列表，供 Thymeleaf 使用
     @ModelAttribute("couponListData") // for select_page.html 和 listAllCoupon.html
     protected List<CouponVO> referenceListData(Model model) {
         List<CouponVO> list = couponSvc.getAll();
         return list;
     }
    
    //任國櫃位管理優惠券
    @ModelAttribute("counterCouponListData")
    protected List<CouponVO> CounterReferenceListData(HttpSession session, Model model) {
        CounterVO counter = (CounterVO) session.getAttribute("counter");
        if (counter != null) {
            List<CouponVO> list = couponSvc.getOneCounter46(counter.getCounterNo());
            return list;
        } else {
            // 如果counter為null，返回一個空列表或處理錯誤
            model.addAttribute("error", "未登錄或Session信息遺失");
            return new ArrayList<>(); // 或者其他適當的錯誤處理
        }
    }


}
