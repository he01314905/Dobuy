package com.monthsettlement.controller;

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

import com.monthsettlement.model.MonthSettlementService;
import com.monthsettlement.model.MonthSettlementVO;

@Controller
@Validated
@RequestMapping("/monthsettlement")
public class MonthSettlementNoController {

    @Autowired
    MonthSettlementService monthSettlementService;

    /*
     * This method will be called on select_page.html form submission, handling POST
     * request It also validates the user input
     */
    @PostMapping("getOneForDisplay")
    public String getOneForDisplay(
            /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
            @NotEmpty(message = "抽成月結編號: 請勿空白")
            @Digits(integer = 10, fraction = 0, message = "抽成月結編號: 請填數字-請勿超過{integer}位數")
            @RequestParam("monthSettlementNo") String monthSettlementNo,
            ModelMap model) {

        /*************************** 2.開始查詢資料 *********************************************/
        MonthSettlementVO monthSettlementVO = monthSettlementService.getOneMonthSettlement(Integer.valueOf(monthSettlementNo));

        List<MonthSettlementVO> list = monthSettlementService.getAll();
        model.addAttribute("monthSettlementListData", list); // for select_page.html 第97 109行用

        if (monthSettlementVO == null) {
            model.addAttribute("errorMessage", "查無資料");
            return "vendor-end/monthsettlement/listAllMonthSettlement";
        }

        /*************************** 3.查詢完成,準備轉交(Send the Success view) *****************/
        model.addAttribute("monthSettlementVO", monthSettlementVO);
        model.addAttribute("getOneForDisplay", "true"); // 旗標getOneForDisplay見select_page.html的第156行

        return "vendor-end/monthsettlement/selectPage"; // 查詢完成後轉交select_page.html由其第158行insert listOneMonthSettlement.html內的th:fragment="listOneMonthSettlement-div"
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e, Model model) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getMessage() + "<br>");
        }
        //==== 以下第92~96行是當前面第77行返回 /src/main/resources/templates/back-end/monthsettlement/select_page.html用的 ====   
        List<MonthSettlementVO> list = monthSettlementService.getAll();
        model.addAttribute("monthSettlementListData", list);     // for select_page.html 第97 109行用
        String message = strBuilder.toString();
        return new ModelAndView("vendor-end/monthsettlement/selectPage", "errorMessage", "請修正以下錯誤:<br>" + message);
    }
}
