package com.coupon.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional; 
import javax.validation.Valid;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import com.coupon.model.CouponVO;
import com.coupondetail.model.CouponDetailService;
import com.coupondetail.model.CouponDetailVO;
import com.goods.model.GoodsService;
import com.goods.model.GoodsVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.msg.model.MsgService;
import com.msg.model.MsgVO;
import com.notice.model.NoticeRepository;
import com.notice.model.NoticeService;
import com.notice.model.NoticeVO;
import com.counter.model.CounterVO;
import com.coupon.model.CouponService;

@Controller
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    CouponService couponSvc;

    @Autowired
    CouponDetailService coupondetailSvc;
    
    @Autowired
    GoodsService goodsSvc;
    
    @Autowired
    MsgService msgService; 
    
    @Autowired
    NoticeService noticeService; 
    
    @Autowired
    MemberService memberService;
        
    @Autowired
    NoticeRepository noticeRepository; 
    
    // 確保 counter 存在的公共方法
    @ModelAttribute("counter")
    public CounterVO populateCounter(HttpSession session) {
        CounterVO counter = (CounterVO) session.getAttribute("counter");
        if (counter == null) {
            counter = new CounterVO(); // 設置一個空的 CounterVO
            counter.setCounterNo(0);   // 設置合理的默認值，避免模板出錯
            counter.setCounterStatus(0); // 適配 Thymeleaf 的 counterStatus
            session.setAttribute("counter", counter); // 將 counter 放回 session
        }
        return counter;
    }
    
    @GetMapping("/addCoupon")
//   從 session 獲取當前登入櫃位資訊
    public String addCoupon(HttpSession session, ModelMap model) {
        CounterVO counter = (CounterVO) session.getAttribute("counter");
        
        // 如果櫃位未登入，重定向到登入頁
        if (counter == null) {
        	
            return "redirect:/counter/login";  // 請根據實際的櫃位登入路徑調整
        }	        	
        if (counter.getCounterStatus() <= 2) {
            return "redirect:/counter/Counterindex"; // 如果 counterStatus 為 2，則重定向
        }
        
        List<GoodsVO> goodsList = goodsSvc.findByCounterVO_CounterNo(counter.getCounterNo());
        model.addAttribute("goodsList", goodsList);
        
        CouponVO couponVO = new CouponVO();
        couponVO.setCounter(counter);  // 設置 CounterVO
        couponVO.setCouponDetails(new ArrayList<>());
        couponVO.getCouponDetails().add(new CouponDetailVO()); // 添加一個空明細
        
        model.addAttribute("couponVO", couponVO);
        model.addAttribute("counter", counter);  // 加入這行，提供給 header 使用
        model.addAttribute("msgSvc", msgService);
        
        return "vendor-end/coupon/addCoupon";
    }
    

 // 櫃位新增優惠券，可以同時設定優惠商品明細--送出
    @PostMapping("/insert")
    public String insertCouponWithDetails(
            @Valid @ModelAttribute("couponVO") CouponVO couponVO,
            BindingResult result,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        CounterVO counter = (CounterVO) session.getAttribute("counter");
        model.addAttribute("msgSvc", msgService);
        if (counter == null) {
            redirectAttributes.addFlashAttribute("error", "請先登入櫃位");
            return "redirect:/counter/login";
        }

        // 設置優惠券狀態
        couponVO.setCouponStatus(1); // 設定預設值（1 = 有效）
        couponVO.setCounter(counter); // 設置櫃位

        Date now = new Date(); // 獲取當前時間
        if (couponVO.getCouponDetails() != null) {
            for (CouponDetailVO detail : couponVO.getCouponDetails()) {
                detail.setCreatedAt(now);
                detail.setUpdatedAt(now);
                detail.setCoupon(couponVO); // 設置外鍵關聯
            }
        }

        // 重新驗證
        if (result.hasErrors()) {
            List<GoodsVO> goodsList = goodsSvc.findByCounterVO_CounterNo(counter.getCounterNo());
            model.addAttribute("goodsList", goodsList);
            model.addAttribute("couponVO", couponVO);
            return "vendor-end/coupon/addCoupon";
        }

        try {
            couponSvc.addCouponWithDetails(couponVO);
            redirectAttributes.addFlashAttribute("success", "優惠券新增成功");
            return "redirect:/coupon/listAllCoupon";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "新增失敗：" + e.getMessage());
            return "redirect:/coupon/addCoupon";
        }
    }

    
    
    
    
    
//    櫃位修改優惠券 可以同時修改優惠商品明細--進入
    @PostMapping("getOne_For_Update")
    public String getOne_For_Update(@RequestParam("couponNo") String couponNo, 
                                    Model model, 
                                    HttpSession session) {
        try {
            // 從 session 中獲取 counter 資料
            CounterVO counter = (CounterVO) session.getAttribute("counter");
            if (counter == null) {
                return "redirect:/counter/login"; // 如果沒有登入，重定向到登入頁面
            }
            model.addAttribute("counter", counter);
            model.addAttribute("msgSvc", msgService);

            // 獲取優惠券數據
            CouponVO couponVO = couponSvc.getOneCouponWithDetails(Integer.valueOf(couponNo));
            
            if (couponVO == null) {
                model.addAttribute("error", "找不到指定的優惠券");
                return "redirect:/coupon/listAllCoupon";
            }
            
            // 確保明細列表不為 null
            if (couponVO.getCouponDetails() == null) {
                couponVO.setCouponDetails(new ArrayList<>());
            }
            // 加載商品列表
            List<GoodsVO> goodsList = goodsSvc.findByCounterVO_CounterNo(counter.getCounterNo());
            model.addAttribute("goodsList", goodsList);
            
            
            // 將數據添加到模型
            model.addAttribute("couponVO", couponVO);
            System.out.println("載入優惠券數據：" + couponVO.getCouponTitle());
            System.out.println("明細數量：" + couponVO.getCouponDetails().size());
            
            return "vendor-end/coupon/updateCoupon";
            
        } catch (Exception e) {
            model.addAttribute("error", "載入數據時發生錯誤：" + e.getMessage());
            return "redirect:/coupon/listAllCoupon";
        }
    }

//    櫃位修改優惠券 可以同時修改優惠商品明細--送出
    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("couponVO") CouponVO couponVO,
                         BindingResult result,
                         Model model,
                         HttpSession session) {
        System.out.println("收到優惠券更新請求：" + couponVO.getCouponNo());
        model.addAttribute("msgSvc", msgService);

        // 從 session 獲取櫃位信息
        CounterVO counter = (CounterVO) session.getAttribute("counter");
        if (counter == null) {
            return "redirect:/counter/login";
        }
        
        // 設置完整的櫃位對象
        couponVO.setCounter(counter);

        // 如果有驗證錯誤則輸出
        if (result.hasErrors()) {
            System.out.println("發現驗證錯誤：");
            for (FieldError error : result.getFieldErrors()) {
                System.out.println("欄位：" + error.getField() + "，錯誤：" + error.getDefaultMessage());
            }

            // 為表單添加必要的屬性
            List<GoodsVO> goodsList = goodsSvc.findByCounterVO_CounterNo(counter.getCounterNo());
            model.addAttribute("goodsList", goodsList);
            model.addAttribute("couponVO", couponVO);
            return "vendor-end/coupon/updateCoupon";
        }

        try {
            // 更新優惠券及其詳細信息
            couponSvc.updateCouponWithDetails(couponVO);
            model.addAttribute("success", "修改成功！");
            return "redirect:/coupon/listAllCoupon";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "修改失敗：" + e.getMessage());
            model.addAttribute("couponVO", couponVO);
            
            // 重新添加表單所需的屬性
            List<GoodsVO> goodsList = goodsSvc.findByCounterVO_CounterNo(counter.getCounterNo());
            model.addAttribute("goodsList", goodsList);
            return "vendor-end/coupon/updateCoupon";
        }
    }
//    @PostMapping("update")
//    public String update(@Valid CouponVO couponVO, 
//                         BindingResult result, 
//                         Model model,
//                         HttpSession session) {
//        System.out.println("Received update request for coupon: " + couponVO.getCouponNo());
//
//        // 從 session 取得 counter 並添加到 model
//        CounterVO counter = (CounterVO) session.getAttribute("counter");
//        if (counter == null) {
//            return "redirect:/counter/login"; // 如果未登入，重定向到登入頁面
//        }
//        model.addAttribute("counter", counter);
//
//        // 確保 couponVO 的 counter 也正確設置
//        if (couponVO.getCounter() == null) {
//            couponVO.setCounter(counter);
//        }
//
//        if (result.hasErrors()) {
//            System.out.println("Validation errors found:");
//            model.addAttribute("couponVO", couponVO);
//            return "vendor-end/coupon/updateCoupon";
//        }
//
//        try {
//            // 更新優惠券和明細
//            CouponVO updatedCoupon = couponSvc.updateCouponWithDetails(couponVO);
//            model.addAttribute("success", "修改成功！");
//            model.addAttribute("couponVO", updatedCoupon);
//            return "vendor-end/coupon/listOneCoupon";
//        } catch (Exception e) {
//            System.out.println("Error updating coupon: " + e.getMessage());
//            e.printStackTrace();
//            model.addAttribute("error", "修改失敗：" + e.getMessage());
//            model.addAttribute("couponVO", couponVO);
//            return "vendor-end/coupon/updateCoupon";
//        }
//    }



    
    /*
     * This method will be called on listAllCoupons.html form submission, handling POST request
     */
    @PostMapping("delete")
    public String delete(@RequestParam("couponNo") String couponNo, ModelMap model) {
    	
        /*************************** 2.開始刪除資料 *****************************************/
        couponSvc.deleteCoupon(Integer.valueOf(couponNo));

        
        
        /*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
        List<CouponVO> list = couponSvc.getAll();
        model.addAttribute("couponListData", list);
        model.addAttribute("success", "- (刪除成功)");
        return "redirect:/coupon/listAllCoupon";
    }

    
  //任國櫃位優惠券管理 櫃位列出自己的優惠券 
    @PostMapping("listCounterCoupons_ByCompositeQuery")
    public String listCounterCoupons(HttpSession session ,HttpServletRequest req, Model model) {
        CounterVO counter = (CounterVO) session.getAttribute("counter");
        List<CouponVO> list = couponSvc.getOneCounter46(counter.getCounterNo());
        model.addAttribute("counterCouponListDat", list); // for listAllEmp.html 第85行用
        return "vendor-end/coupon/listAllCoupon";
    }
    
    
    
 // 後台審核優惠券的 GET 方法---顯示頁面
    @GetMapping("/approve")
    public String showApprovePage(Model model,HttpSession session) {
    	if(session.getAttribute("managerNo")==null) {
			return "redirect:/login/Login";
		}
	
        // 獲取所有優惠券列表
        List<CouponVO> list = couponSvc.getAll();
        // 將數據添加到模型中，注意這裡的屬性名要跟視圖中使用的一致
        model.addAttribute("couponListData", list);
        return "back-end/coupon/couponApprove";
    }
    
    
//   後台審核優惠券---送出修改資料、通知審核成功訊息給櫃位
    @PostMapping("/approve")
    public String approveCoupon(@RequestParam("couponNo") int couponNo, RedirectAttributes redirectAttributes) {
        try {
            // 1. 驗證並審核優惠券
            boolean isApproved = couponSvc.approveCoupon(couponNo);
            if (isApproved) {
                // 2. 獲取優惠券資訊
                CouponVO couponVO = couponSvc.getOneCouponWithDetails(couponNo);
                if (couponVO != null && couponVO.getCounter() != null) {
                    // 3. 發送通知到該櫃位
                    Integer counterNo = couponVO.getCounter().getCounterNo();
                    String informMsg = "您的優惠券【" + couponVO.getCouponTitle() + "】已通過審核，現可進行使用！";

                    MsgVO msgVO = new MsgVO();
                    msgVO.setCounterNo(counterNo);
                    msgVO.setInformMsg(informMsg);
                    msgVO.setInformDate(new Timestamp(System.currentTimeMillis())); // 設定發送時間
                    msgVO.setInformRead((byte) 0); // 設定為未讀

                    msgService.addMsg(msgVO); // 保存通知
                }

                redirectAttributes.addFlashAttribute("message", "審核成功，已發送通知給櫃位。");
            } else {
                redirectAttributes.addFlashAttribute("message", "審核失敗！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "審核時發生錯誤：" + e.getMessage());
        }

        return "redirect:/coupon/approve"; // 重定向到審核列表頁面
    }
 
    
 // 後台審核優惠券 可查看單個優惠券詳情
    @GetMapping("/approve/{couponNo}")
    public String viewCouponDetail(@PathVariable int couponNo, Model model) {
        CouponVO coupon = couponSvc.getOneCouponWithDetails(couponNo);
        
        // 先檢查優惠券是否存在
        if (coupon == null) {
            return "redirect:/coupon/approve";
        }

        // 使用優惠券的 CouponDetails 集合，而不是重新查詢
        List<CouponDetailVO> details = new ArrayList<>(coupon.getCouponDetails());


        model.addAttribute("coupon", coupon);
        model.addAttribute("couponDetails", details);
        
        return "back-end/coupondetail/couponDetail";
    }

    // 推播通知到會員中心
    @PostMapping("/pushNotification")
    public String pushNotification(@RequestParam("couponNo") Integer couponNo, RedirectAttributes redirectAttributes) {
        try {
            CouponVO coupon = couponSvc.getOneCouponWithDetails(couponNo);
            if (coupon == null) {
                redirectAttributes.addFlashAttribute("error", "優惠券不存在，無法推送通知。");
                return "redirect:/coupon/approve";
            }

            String message = "全新優惠券【" + coupon.getCouponTitle() + "】現已上架！立即查看並領取優惠。";

            // 取得所有會員清單
            List<MemberVO> allMembers = memberService.getAll();
            System.out.println("會員清單總數：" + allMembers.size());

            List<Integer> memNos = allMembers.stream().map(MemberVO::getMemNo).distinct().collect(Collectors.toList());
            System.out.println("去重後會員數量：" + memNos.size());

            // 查詢已存在通知的會員
            List<Integer> existingMemNos = noticeRepository.findExistingMemNosByContent(message, memNos);
            System.out.println("已存在通知的會員數：" + existingMemNos.size());

            // 過濾未推送的會員
            List<NoticeVO> newNotices = new ArrayList<>();
            for (Integer memNo : memNos) {
                if (!existingMemNos.contains(memNo)) {
                    NoticeVO notice = new NoticeVO();
                    notice.setMemNo(memNo);
                    notice.setNoticeContent(message);
                    notice.setNoticeRead((byte) 0);
                    notice.setNoticeDate(new Timestamp(System.currentTimeMillis()));
                    newNotices.add(notice);
                }
            }
            noticeRepository.saveAll(newNotices);

            System.out.println("新增通知數量：" + newNotices.size());
            redirectAttributes.addFlashAttribute("success", "通知已成功推送至所有會員。");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "推播通知時發生錯誤：" + e.getMessage());
        }

        return "redirect:/coupon/approve";
    }


}
