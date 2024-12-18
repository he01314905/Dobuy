package com.member.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.member.model.MemberVO.LoginGroup;
import com.memcoupon.model.MemCouponService;

@Controller
@RequestMapping("/mem")
public class MemberLoginController {
	@Autowired
	MemberService memberSvc;
//	winfred===================================================================================以下	
	@Autowired
	MemCouponService memCouponSvc;

//	winfred===================================================================================以上	
	@PostMapping("/loginCheck")
	public String loginCheck(@Validated(LoginGroup.class) @ModelAttribute MemberVO memberVO, BindingResult result,
			Model model, HttpSession session, HttpServletRequest req) {
		List<String> errorMsgs = new LinkedList<String>();
		if (result.hasErrors()) {
			return "front-end/member/login";
		}

		if (!memberSvc.isAccountExists(memberVO.getMemAccount())) {
			errorMsgs.add("帳號不存在");
			model.addAttribute("errorMsgs", errorMsgs);
			return "front-end/member/login";
		}
		

		if (!memberSvc.validateLogin(memberVO.getMemAccount(), memberVO.getMemPassword())) {
			errorMsgs.add("密碼錯誤");
			model.addAttribute("errorMsgs", errorMsgs);
			return "front-end/member/login";
		}
		 Integer memStatus = memberSvc.getMemStatusByAccount(memberVO.getMemAccount());
		    if (memStatus != null && memStatus == 0) {  // 如果狀態為 0
		        errorMsgs.add("帳號已停權，請聯繫管理員。");
		        model.addAttribute("errorMsgs", errorMsgs);
		        return "front-end/member/login";
		    }
		// 登錄成功，設置 session
		session.setAttribute("memAccount", memberVO.getMemAccount());
		session.setAttribute("memNo", memberSvc.getMemNoByAccount(memberVO.getMemAccount())); // 用memAccount去找memNo
		session.setAttribute("memStatus", memberSvc.getMemStatusByAccount(memberVO.getMemAccount()));
		 // 檢查是否有原始請求
		
//	    String originalRequest = (String) session.getAttribute("originalRequest");
//	    if (originalRequest != null) {
//	        session.removeAttribute("originalRequest"); // 移除原始請求
//	        return "redirect:" + originalRequest.replaceFirst(req.getContextPath(), "");
//	    }
		
		
	    // 如果沒有原始請求，跳轉到默認頁面
		return "redirect:/home"; // 重定向到成功页面

	}

//	winfred===================================================================================以下
//	 特別處理優惠券相關的登入
	@PostMapping("/loginCheck49")
	public String loginCheck49(@Validated(LoginGroup.class) @ModelAttribute MemberVO memberVO,
	                         BindingResult result, Model model,
	                         HttpSession session, HttpServletRequest req) {
	    System.out.println("===== loginCheck49 Start =====");
	    System.out.println("Received Account: " + memberVO.getMemAccount());
	    System.out.println("Received Password: " + (memberVO.getMemPassword() != null ? "not null" : "null"));

	    // 在設置 session 之前打印 memNo
	    System.out.println("Member NO: " + memberVO.getMemNo());
	    session.setAttribute("memAccount", memberVO.getMemAccount());
	    session.setAttribute("memNo", memberVO.getMemNo());
	    
	    // 打印 session 內容確認
	    System.out.println("Session memAccount: " + session.getAttribute("memAccount"));
	    System.out.println("Session memNo: " + session.getAttribute("memNo"));
	    
	    // 檢查原始請求

	    System.out.println("===== loginCheck49 End =====");
	    
		System.out.println("Entering loginCheck49 method..."); // 添加日誌
	    
	    
	    List<String> errorMsgs = new LinkedList<String>();
	    
	    if (result.hasErrors()) {
	        System.out.println("Validation errors found"); // 添加日誌
	        return "front-end/mem/login";
	    }
	    
	    if (!memberSvc.isAccountExists(memberVO.getMemAccount())) {
	        System.out.println("Account does not exist: " + memberVO.getMemAccount()); // 添加日誌
	        errorMsgs.add("帳號不存在");
	        model.addAttribute("errorMsgs", errorMsgs);
	        return "front-end/mem/login";
	    }
	    
	    if (!memberSvc.validateLogin(memberVO.getMemAccount(), memberVO.getMemPassword())) {
	        System.out.println("Password incorrect for account: " + memberVO.getMemAccount()); // 添加日誌
	        errorMsgs.add("密碼錯誤");
	        model.addAttribute("errorMsgs", errorMsgs);
	        return "front-end/mem/login";
	    }
	    
	    // 登入成功
	    System.out.println("Login successful for account: " + memberVO.getMemAccount()); // 添加日誌
	    session.setAttribute("memAccount", memberVO.getMemAccount());
	    session.setAttribute("memNo", memberVO.getMemNo());
	    
	    // 處理優惠券相關邏輯
	    String originalRequest = (String) session.getAttribute("originalRequest");
	    System.out.println("Original request: " + originalRequest); // 添加日誌

	    if (originalRequest != null && originalRequest.contains("/claim")) {
	        try {
	            session.removeAttribute("originalRequest");
	            String couponNo = originalRequest.split("couponNo=")[1];
	            System.out.println("Claiming coupon: " + couponNo); // 添加日誌
	            
	            MemberVO member = memberSvc.findByMemAccount(memberVO.getMemAccount());
	            memCouponSvc.claimCoupon(member.getMemNo(), Integer.parseInt(couponNo));
	            
	            return "redirect:/memcoupon/memListAllCoupon";
	        } catch (Exception e) {
	            System.out.println("Error claiming coupon: " + e.getMessage()); // 添加日誌
	            model.addAttribute("error", e.getMessage());
	            return "redirect:/memcoupon/memListAllCoupon";
	        }
	    }

	    System.out.println("No coupon claim request, redirecting to default page"); // 添加日誌
	    return "redirect:/memcoupon/memListAllCoupon";
	}
	
	@GetMapping("/login49")  // 會變成 /mem/login49
	public String showLogin49Page(Model model) {
	    model.addAttribute("memberVO", new MemberVO());
	    return "front-end/member/login49";  // 這是視圖路徑，不需要 /mem
	}
//	winfred===================================================================================以上
	
	
}
