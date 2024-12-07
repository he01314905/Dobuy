package com.coupondetail.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.coupondetail.model.CouponDetailVO;
import com.counter.model.CounterVO;
import com.coupon.model.CouponService;
import com.coupon.model.CouponVO;
import com.coupondetail.model.CouponDetailService;

@Controller
@RequestMapping("/coupondetail")
public class CouponDetailController {

	
    @Autowired
    CouponService couponSvc;
    
    @Autowired
    CouponDetailService couponDetailSvc;

    
//    @PostMapping("/addCoupon")
//    public String addCoupon(@ModelAttribute CouponVO coupon, Model model) {
//        try {
//        	couponSvc.addCouponWithDetails(coupon);
//            model.addAttribute("successMessage", "Coupon added successfully!");
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "Failed to add coupon: " + e.getMessage());
//        }
//        return "couponForm";
//    }
    
    
    
    /*
     * This method will serve as addCouponDetail.html handler.
     */
    
//    @GetMapping("addCouponDetail")
//    public String addCouponDetail(ModelMap model) {
//        CouponDetailVO couponDetailVO = new CouponDetailVO();
//        model.addAttribute("couponDetailVO", couponDetailVO);
//        return "vendor-end/coupondetail/addCouponDetail";
//    }

    //靜態用
//    @GetMapping("/coupondetail/addCouponDetail")
//    public String addCouponDetail(Model model) {
//        return "vendor-end/coupondetail/addCouponDetail";
//    }
    
    
    
    /*
     * This method will be called on addCouponDetail.html form submission, handling POST request
     * It also validates the user input
     */
//    @PostMapping("insert")
//    public String insert(@Valid CouponDetailVO couponDetailVO, BindingResult result, ModelMap model) throws IOException {
//
//        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//
//        if (result.hasErrors() ) {
//            return "vendor-end/coupondetail/addCouponDetail";
//        }
//
//        /*************************** 2.開始新增資料 *****************************************/
//        couponDetailSvc.addCouponDetail(couponDetailVO);
//
//        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
//        List<CouponDetailVO> list = couponDetailSvc.getAll();
//        model.addAttribute("couponDetailListData", list);
//        model.addAttribute("success", "- (新增成功)");
//        return "redirect:/coupondetail/listAllCouponDetail";
//    }

    /*
     * This method will be called on listAllCouponDetails.html form submission, handling POST request
     */
//    @PostMapping("getOne_For_Update")
//    public String getOne_For_Update(@RequestParam("couponDetailNo") String couponDetailNo, ModelMap model) {
//        /*************************** 2.開始查詢資料 *****************************************/
//        CouponDetailVO couponDetailVO = couponDetailSvc.getOneCouponDetail(Integer.valueOf(couponDetailNo));
//
//        /*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
//        model.addAttribute("couponDetailVO", couponDetailVO);
//        return "vendor-end/coupondetail/update_couponDetail_input";
//    }

    /*
     * This method will be called on update_couponDetail_input.html form submission, handling POST request
     * It also validates the user input
     */
//    @PostMapping("update")
//    public String update(@Valid CouponDetailVO couponDetailVO, BindingResult result, ModelMap model) throws IOException {
//
//        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//        result = removeFieldError(couponDetailVO, result, "upFiles");
//
//        if (result.hasErrors()) {
//            return "back-end/coupondetail/update_couponDetail_input";
//        }
//
//        /*************************** 2.開始修改資料 *****************************************/
//        couponDetailSvc.updateCouponDetail(couponDetailVO);
//
//        /*************************** 3.修改完成,準備轉交(Send the Success view) **************/
//        model.addAttribute("success", "- (修改成功)");
//        couponDetailVO = couponDetailSvc.getOneCouponDetail(Integer.valueOf(couponDetailVO.getCouponDetailNo()));
//        model.addAttribute("couponDetailVO", couponDetailVO);
//        return "vendor-end/coupondetail/listOneCouponDetail";
//    }

    /*
     * This method will be called on listAllCouponDetails.html form submission, handling POST request
     */
    @PostMapping("delete")
    public String delete(@RequestParam("couponDetailNo") String couponDetailNo, ModelMap model) {
        /*************************** 2.開始刪除資料 *****************************************/
        couponDetailSvc.deleteCouponDetail(Integer.valueOf(couponDetailNo));

        /*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
        List<CouponDetailVO> list = couponDetailSvc.getAll();
        model.addAttribute("couponDetailListData", list);
        model.addAttribute("success", "- (刪除成功)");
        return "vendor-end/coupondetail/listAllCouponDetail";
    }

    
    
    
    
    /*
     * This method will be called on select_page.html form submission, handling POST request
     */
//    @PostMapping("listCouponDetail_ByCompositeQuery")
//    public String listAllCouponDetail(HttpServletRequest req, Model model) {
//        Map<String, String[]> map = req.getParameterMap();
//        List<CouponDetailVO> list = couponDetailSvc.getAll(map);
//        model.addAttribute("couponDetailListData", list);
//        return "vendor-end/coupondetail/listAllCouponDetail";
//    }

    // 去除BindingResult中某個欄位的FieldError紀錄
//    public BindingResult removeFieldError(CouponDetailVO couponDetailVO, BindingResult result, String removedFieldname) {
//        List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
//                .filter(fieldname -> !fieldname.getField().equals(removedFieldname))
//                .collect(Collectors.toList());
//        result = new BeanPropertyBindingResult(couponDetailVO, "couponDetailVO");
//        for (FieldError fieldError : errorsListToKeep) {
//            result.addError(fieldError);
//        }
//        return result;
//    }
    
    // 櫃位列出自己的優惠券點詳情 可以查看優惠商品明細
    @GetMapping("/listByCouponNo") 
    public String listByCouponNo(@RequestParam("couponNo") Integer couponNo, 
                               HttpSession session, 
                               Model model) {
       // 檢查櫃位登入狀態
       CounterVO counter = (CounterVO) session.getAttribute("counter");
       if (counter == null) {
           return "redirect:/counter/login";
       }
       
       try {
           List<CouponDetailVO> details = couponDetailSvc.getByCouponNo(couponNo);
           model.addAttribute("couponDetails", details);
           model.addAttribute("counter", counter);  // 提供給header使用
           return "vendor-end/coupondetail/listCouponDetail";
       } catch (Exception e) {
           e.printStackTrace();
           model.addAttribute("error", "查詢明細失敗：" + e.getMessage());
           return "error";
       }
    }

}
