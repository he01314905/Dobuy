package com.discount.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

import com.discount.model.DiscountVO;
import com.discount.model.DiscountService;

@Controller
@RequestMapping("/discount")
public class DiscountController {

    @Autowired
    DiscountService discountService;
    
    // 新增檢查管理員登入的方法
    private boolean isManagerLoggedIn(HttpSession session) {
        return session.getAttribute("managerNo") != null;
    }
    
    
    

    @GetMapping("addDiscount")
    public String addDiscount(ModelMap model) {
        model.addAttribute("discountVO", new DiscountVO()); // 初始化空物件
        return "back-end/discount/addDiscount";
    }

    
    
    
    @PostMapping("insert")
    public String insert(@Valid DiscountVO discountVO, 
                        BindingResult result, 
                        ModelMap model) {
        // 先處理基本驗證錯誤
        if (result.hasErrors()) {
            model.addAttribute("discountVO", discountVO);
            return "back-end/discount/addDiscount";
        }

        try {
            // 額外的業務邏輯驗證
            if (discountVO.getDisStart() != null && discountVO.getDisEnd() != null) {
                if (discountVO.getDisStart().after(discountVO.getDisEnd())) {
                    result.rejectValue("disEnd", "error.date", 
                                     "結束日期必須晚於開始日期");
                    model.addAttribute("discountVO", discountVO);
                    return "back-end/discount/addDiscount";
                }
            }

            // 設置預設值
            discountVO.setDisStatus(0);
            discountVO.setCreatedAt(new Date());
            discountVO.setUpdatedAt(new Date());

            // 保存數據
            discountService.addDiscount(discountVO);
            return "redirect:/discount/listAllDiscount";

        } catch (Exception e) {
            // 添加錯誤訊息到 model
            model.addAttribute("error", "新增失敗: " + e.getMessage());
            model.addAttribute("discountVO", discountVO);
            return "back-end/discount/addDiscount";
        }
    }

    // 前往修改頁面
    @PostMapping("getOne_For_Update")
    public String getOne_For_Update(@RequestParam("disNo") String disNo, 
                                  ModelMap model,
                                  HttpSession session) {
        // 檢查登入狀態
        if (!isManagerLoggedIn(session)) {
            return "redirect:/login";
        }
        
        try {
            // 查詢優惠資料
            DiscountVO discountVO = discountService.getOneDiscount(Integer.valueOf(disNo));
            
            if (discountVO == null) {
                model.addAttribute("error", "找不到指定的優惠資料");
                return "back-end/discount/listAllDiscount";
            }
            
            model.addAttribute("discountVO", discountVO);
            return "back-end/discount/update_discount_input";
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "查詢資料時發生錯誤: " + e.getMessage());
            return "back-end/discount/listAllDiscount";
        }
    }

    // 處理修改資料
    @PostMapping("update")
    public String update(@Valid DiscountVO discountVO, 
                        BindingResult result, 
                        ModelMap model,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        
        if (!isManagerLoggedIn(session)) {
            return "redirect:/login";
        }

        try {
            // 1. 基本驗證
            if (result.hasErrors()) {
                model.addAttribute("discountVO", discountVO);
                return "back-end/discount/update_discount_input";
            }

            // 2. 日期邏輯驗證
            Date currentDate = new Date();
            if (discountVO.getDisStart() != null && discountVO.getDisEnd() != null) {
                // 開始日期不能早於當前日期
                if (discountVO.getDisStart().before(currentDate)) {
                    result.rejectValue("disStart", "error.disStart", "開始日期不能早於當前日期");
                }
                // 結束日期必須晚於開始日期
                if (discountVO.getDisStart().after(discountVO.getDisEnd())) {
                    result.rejectValue("disEnd", "error.disEnd", "結束日期必須晚於開始日期");
                }
            }

            // 3. 折扣率驗證
            if (discountVO.getDisRate() != null) {
                if (discountVO.getDisRate() < 0.01 || discountVO.getDisRate() > 1.00) {
                    result.rejectValue("disRate", "error.disRate", "折扣率必須在0.01到1.00之間");
                }
            }

            // 4. 名稱和內容長度驗證
            if (discountVO.getDisTitle() != null && discountVO.getDisTitle().length() > 255) {
                result.rejectValue("disTitle", "error.disTitle", "優惠名稱長度不能超過255個字元");
            }
            if (discountVO.getDisContext() != null && discountVO.getDisContext().length() > 255) {
                result.rejectValue("disContext", "error.disContext", "優惠內容長度不能超過255個字元");
            }

            // 5. 使用條件描述驗證
            if (discountVO.getDescLimit() != null && discountVO.getDescLimit().length() > 255) {
                result.rejectValue("descLimit", "error.descLimit", "使用條件描述長度不能超過255個字元");
            }

            // 如果有任何驗證錯誤，返回表單
            if (result.hasErrors()) {
                model.addAttribute("discountVO", discountVO);
                // 收集所有錯誤訊息
                List<String> errorMessages = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
                model.addAttribute("errorMessages", errorMessages);
                return "back-end/discount/update_discount_input";
            }

            // 保存原有的創建時間
            DiscountVO originalDiscount = discountService.getOneDiscount(discountVO.getDisNo());
            if (originalDiscount != null) {
                discountVO.setCreatedAt(originalDiscount.getCreatedAt());
            }
            
            // 更新修改時間
            discountVO.setUpdatedAt(new Date());

            // 更新資料
            discountService.updateDiscount(discountVO);
            
            // 設置成功訊息
            redirectAttributes.addFlashAttribute("success", "修改成功");
            return "redirect:/discount/listAllDiscount";
            
        } catch (Exception e) {
            model.addAttribute("error", "修改資料時發生錯誤: " + e.getMessage());
            model.addAttribute("discountVO", discountVO);
            return "back-end/discount/update_discount_input";
        }
    }
    
    @PostMapping("delete")
    public String delete(@RequestParam("disNo") String disNo, ModelMap model) {
        /*************************** 2.開始刪除資料 *****************************************/
        discountService.deleteDiscount(Integer.valueOf(disNo));

        /*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
        List<DiscountVO> list = discountService.getAll();
        model.addAttribute("discountListData", list);
        model.addAttribute("success", "- (刪除成功)");
        return "back-end/discount/listAllDiscount";
    }


}
