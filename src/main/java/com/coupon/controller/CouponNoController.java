//package com.coupon.controller;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import javax.validation.constraints.Digits;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotEmpty;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.*;
//import com.coupon.model.CouponVO;
//import com.coupon.model.CouponService;
//
//@Controller
//@Validated
//@RequestMapping("/coupon")
//public class CouponNoController {
//
//    @Autowired
//    CouponService couponSvc;
//
//    /*
//     * This method will be called on select_page.html form submission, handling POST request
//     * It also validates the user input
//     */
//    @PostMapping("getOne_For_Display")
//    public String getOne_For_Display(
//        /***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
//        @NotEmpty(message = "優惠券編號: 請勿空白")
//        @Digits(integer = 5, fraction = 0, message = "優惠券編號: 請填數字-請勿超過{integer}位數")
//        @Min(value = 1, message = "優惠券編號: 不能小於{value}")
//        @Max(value = 99999, message = "優惠券編號: 不能超過{value}")
//        @RequestParam("couponNo") String couponNo, ModelMap model) {
//
//        /***************************2.開始查詢資料*********************************************/
//        CouponVO couponVO = couponSvc.getOneCoupon(Integer.valueOf(couponNo));
//
//        List<CouponVO> list = couponSvc.getAll();
//        model.addAttribute("couponListData", list); // for select_page.html 下拉選單
//        if (couponVO == null) {
//            model.addAttribute("errorMessage", "查無資料");
//            return "vendor-end/coupon/select_page";
//        }
//
//        /***************************3.查詢完成,準備轉交(Send the Success view)*****************/
//        model.addAttribute("couponVO", couponVO);
//        model.addAttribute("getOne_For_Display", "true"); // Flag for displaying specific data
//        return "vendor-end/coupon/select_page";
//    }
//
//    /*
//     * This method handles exceptions from constraint violations during form submissions.
//     */
//    @ExceptionHandler(value = { ConstraintViolationException.class })
//    public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e, Model model) {
//        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
//        StringBuilder strBuilder = new StringBuilder();
//        for (ConstraintViolation<?> violation : violations) {
//            strBuilder.append(violation.getMessage() + "<br>");
//        }
//
//        //==== 处理时重新加载页面所需数据 ====
//        List<CouponVO> list = couponSvc.getAll();
//        model.addAttribute("couponListData", list); // for select_page.html 下拉選單
//        String message = strBuilder.toString();
//        return new ModelAndView("vendor-end/coupon/select_page", "errorMessage", "請修正以下錯誤:<br>" + message);
//    }
//}
