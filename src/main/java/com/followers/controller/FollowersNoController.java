package com.followers.controller;

import javax.servlet.http.HttpServletRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import com.followers.model.FollowersService;
import com.followers.model.FollowersVO;

@Controller
@Validated
@RequestMapping("/followers")
public class FollowersNoController {

    @Autowired
    FollowersService followersService;

    /*
     * This method will be called on select_page.html form submission, handling POST
     * request It also validates the user input
     */
    @PostMapping("/getOneForDisplay")
    public String getOneForDisplay(
            /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
            @NotEmpty(message = "櫃位追蹤清單編號: 請勿空白")
            @Digits(integer = 10, fraction = 0, message = "櫃位追蹤清單編號: 請填數字-請勿超過{integer}位數")
            @RequestParam("trackListNo") String trackListNo,
            ModelMap model) {

        /*************************** 2.開始查詢資料 *********************************************/
        FollowersVO followersVO = followersService.getOneFollowers(Integer.valueOf(trackListNo));

        List<FollowersVO> list = followersService.getAll();
        model.addAttribute("followersListData", list); // for select_page.html 第97 109行用

        if (followersVO == null) {
            model.addAttribute("errorMessage", "查無資料");
            return "vendor-end/followers/selectPage";
        }

        /*************************** 3.查詢完成,準備轉交(Send the Success view) *****************/
        model.addAttribute("followersVO", followersVO);
        model.addAttribute("getOneForDisplay", "true"); // 旗標getOneForDisplay見select_page.html的第156行

        return "vendor-end/followers/selectPage"; // 查詢完成後轉交select_page.html由其第158行insert listOneFollowers.html內的th:fragment="listOneFollowers-div"
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e, Model model) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getMessage() + "<br>");
        }
        //==== 以下第92~96行是當前面第77行返回 /src/main/resources/templates/back-end/followers/select_page.html用的 ====   
        List<FollowersVO> list = followersService.getAll();
        model.addAttribute("followersListData", list);     // for select_page.html 第97 109行用
        String message = strBuilder.toString();
        return new ModelAndView("vendor-end/followers/selectPage", "errorMessage", "請修正以下錯誤:<br>" + message);
    }
}
