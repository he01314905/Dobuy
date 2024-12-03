package com.followers.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.followers.model.FollowersService;
import com.followers.model.FollowersVO;
import com.monthsettlement.model.MonthSettlementVO;

@Controller
@RequestMapping("/followers")
public class FollowersController {

    @Autowired
    FollowersService followersService;

    @GetMapping("/addFollowers")
    public String addFollowers(ModelMap model) {
        FollowersVO followersVO = new FollowersVO();
        model.addAttribute("followersVO", followersVO);
        return "vendor-end/followers/addFollowers";
    }

    @PostMapping("insert")
    public String insert(@Valid FollowersVO followersVO, BindingResult result, ModelMap model) throws IOException {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
    	if (result.hasErrors()) {
            model.addAttribute("followersVO", followersVO); // 驗證失敗時返回錯誤的 FollowersVO
            return "vendor-end/followers/addFollowers";
    	}
        /*************************** 2.開始新增資料 *****************************************/
        followersService.addFollowers(followersVO);
        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
        List<FollowersVO> list = followersService.getAll();
        model.addAttribute("followersListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "vendor-end/followers/listAllFollowers";
    }

    @PostMapping("getOneForUpdate")
    public String getOneForUpdate(@RequestParam("trackListNo") String trackListNo, ModelMap model) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        /*************************** 2.開始查詢資料 *****************************************/
        FollowersVO followersVO = followersService.getOneFollowers(Integer.valueOf(trackListNo));
        /*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
        model.addAttribute("followersVO", followersVO);
        return "vendor-end/followers/update-Followers-Input";
    }

    @PostMapping("update")
    public String update(@Valid FollowersVO followersVO, BindingResult result, ModelMap model) throws IOException {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        
        /*************************** 2.開始修改資料 *****************************************/
        followersService.updateFollowers(followersVO);
        /*************************** 3.修改完成,準備轉交(Send the Success view) **************/
        model.addAttribute("success", "- (修改成功)");
        followersVO = followersService.getOneFollowers(Integer.valueOf(followersVO.getTrackListNo()));
        model.addAttribute("followersVO", followersVO);
        return "vendor-end/followers/listOneFollowers";
    }

    @PostMapping("delete")
    public String delete(@RequestParam("trackListNo") String trackListNo, ModelMap model) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        /*************************** 2.開始刪除資料 *****************************************/
        followersService.deleteFollowers(Integer.valueOf(trackListNo));
        /*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
        List<FollowersVO> list = followersService.getAll();
        model.addAttribute("followersListData", list);
        model.addAttribute("success", "- (刪除成功)");
        return "vendor-end/followers/listAllFollowers";
    }
    
    @GetMapping("/listAllFollowers")
    public String listAll(@Valid FollowersVO followersVO, BindingResult result, ModelMap model)
     	{
        /*************************** 2. 開始新增資料 **********************************/
        /*************************** 3. 新增完成，重導至清單頁 ***********************/
        List<FollowersVO> list = followersService.getAll();
        model.addAttribute("followersListData", list);
        model.addAttribute("success", "- (新增成功)");
        System.out.println(followersVO.getTrackListNo());
        return "vendor-end/followers/listAllFollowers";
    }
    
    @PostMapping("/listByCompositeQuery")
    public String listByCompositeQuery(HttpServletRequest req, Model model) {
        Map<String, String[]> map = req.getParameterMap();
        List<FollowersVO> list = followersService.getAll();
        model.addAttribute("followersListData", list);
        return "vendor-end/followers/listAllFollowers";
    }

    // 去除BindingResult中某個欄位的FieldError紀錄
    public BindingResult removeFieldError(FollowersVO followersVO, BindingResult result, String removedFieldName) {
        List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
                .filter(fieldName -> !fieldName.getField().equals(removedFieldName))
                .collect(Collectors.toList());
        result = new BeanPropertyBindingResult(followersVO, "followersVO");
        for (FieldError fieldError : errorsListToKeep) {
            result.addError(fieldError);
        }
        return result;
    }
    
    @GetMapping("/selectPage")
    public String selectPage(Model model) {
        // 取得所有追蹤清單資料供下拉式選單使用
        List<FollowersVO> list = followersService.getAll();
        model.addAttribute("followersListData", list);
        
        // 新增一個空的 FollowersVO 物件供表單綁定使用
        FollowersVO followersVO = new FollowersVO();
        model.addAttribute("followersVO", followersVO);
        
        return "vendor-end/followers/selectPage";
    }
    
//    @PostMapping("/getOneForDisplay")
//    public String getOneForDisplay(
//            /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
//            @NotEmpty(message = "櫃位追蹤清單編號: 請勿空白")
//            @Digits(integer = 10, fraction = 0, message = "櫃位追蹤清單編號: 請填數字-請勿超過{integer}位數")
//            @RequestParam("trackListNo") String trackListNo,
//            ModelMap model) {
//
//        /*************************** 2.開始查詢資料 *********************************************/
//        FollowersVO followersVO = followersService.getOneFollowers(Integer.valueOf(trackListNo));
//
//        List<FollowersVO> list = followersService.getAll();
//        model.addAttribute("followersListData", list); // for select_page.html 第97 109行用
//
//        if (followersVO == null) {
//            model.addAttribute("errorMessage", "查無資料");
//            return "vendor-end/followers/selectPage";
//        }
//
//        /*************************** 3.查詢完成,準備轉交(Send the Success view) *****************/
//        model.addAttribute("followersVO", followersVO);
//        model.addAttribute("getOneForDisplay", "true"); // 旗標getOneForDisplay見select_page.html的第156行
//
//        return "vendor-end/followers/selectPage"; // 查詢完成後轉交select_page.html由其第158行insert listOneFollowers.html內的th:fragment="listOneFollowers-div"
//    }
//    
//    @ExceptionHandler(value = { ConstraintViolationException.class })
//    public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e, Model model) {
//        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
//        StringBuilder strBuilder = new StringBuilder();
//        for (ConstraintViolation<?> violation : violations) {
//            strBuilder.append(violation.getMessage() + "<br>");
//        }
//        //==== 以下第92~96行是當前面第77行返回 /src/main/resources/templates/back-end/followers/select_page.html用的 ====   
//        List<FollowersVO> list = followersService.getAll();
//        model.addAttribute("followersListData", list);     // for select_page.html 第97 109行用
//        String message = strBuilder.toString();
//        return new ModelAndView("vendor-end/followers/selectPage", "errorMessage", "請修正以下錯誤:<br>" + message);
//    }
}
