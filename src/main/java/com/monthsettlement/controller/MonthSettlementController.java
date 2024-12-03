package com.monthsettlement.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.followers.model.FollowersVO;
import com.monthsettlement.model.MonthSettlementService;
import com.monthsettlement.model.MonthSettlementVO;



@Controller
@RequestMapping("/monthsettlement")
public class MonthSettlementController {

    @Autowired
    MonthSettlementService monthSettlementService;

    @Autowired
    MonthSettlementService counterService;

    // 進入新增頁面
    @GetMapping("/vendor-end/monthsettlement/addMonthSettlement")
    public String addMonthSettlement(ModelMap model) {
        MonthSettlementVO monthSettlementVO = new MonthSettlementVO();
        System.out.println("=====");
        model.addAttribute("monthSettlementVO", monthSettlementVO);
        return "vendor-end/monthsettlement/addMonthSettlement";
    }

    // 新增資料處理
    @PostMapping("insert")
    public String insert(@Valid MonthSettlementVO monthsettlementVO, BindingResult result, ModelMap model)
     	{
    	System.out.println("呼叫");
        /*************************** 1. 接收請求參數 - 格式驗證 ************************/
    	
        if (result.hasErrors()) {
        	System.out.println(monthsettlementVO.getCounterNo());
            return "vendor-end/monthsettlement/addMonthSettlement";
        }

        /*************************** 2. 開始新增資料 **********************************/
        monthSettlementService.addMonthSettlement(monthsettlementVO);
        System.out.println(monthsettlementVO.getComm());
        /*************************** 3. 新增完成，重導至清單頁 ***********************/
        List<MonthSettlementVO> list = monthSettlementService.getAll();
        model.addAttribute("monthsettlementData", list);
        model.addAttribute("success", "- (新增成功)");
        System.out.println(monthsettlementVO.getMonth());
        return "vendor-end/monthsettlement/listAllMonthSettlement";
    }
    
    @GetMapping("listAllMonthSettlement")
    public String listAll(@Valid MonthSettlementVO monthsettlementVO, BindingResult result, ModelMap model)
     	{
        /*************************** 2. 開始新增資料 **********************************/
        /*************************** 3. 新增完成，重導至清單頁 ***********************/
        List<MonthSettlementVO> list = monthSettlementService.getAll();
        model.addAttribute("monthsettlementData", list);
        model.addAttribute("success", "- (新增成功)");
        System.out.println(monthsettlementVO.getMonth());
        return "vendor-end/monthsettlement/listAllMonthSettlement";
    }
    
    @GetMapping("selectPage")
    public String selectPage(Model model) {
        // 取得所有追蹤清單資料供下拉式選單使用
        List<MonthSettlementVO> list = monthSettlementService.getAll();
        model.addAttribute("monthsettlementData", list);
        
        // 新增一個空的 FollowersVO 物件供表單綁定使用
        MonthSettlementVO monthsettlementVO = new MonthSettlementVO();
        model.addAttribute("MonthSettlementVO", monthsettlementVO);
        
        return "vendor-end/monthsettlement/selectPage";
    }
    
    // 更新資料頁面
    @PostMapping("getOneForUpdate")
    public String getOneForUpdate(@RequestParam("monthsettlementNo") String monthsettlementNo, ModelMap model) {
        MonthSettlementVO monthSettlementVO = monthSettlementService.getOneMonthSettlement(Integer.valueOf(monthsettlementNo));
        model.addAttribute("monthSettlementVO", monthSettlementVO);
        return "vendor-end/monthsettlement/update-MonthSettlement-Input";
    }

    // 更新資料處理
    @PostMapping("update")
    public String update(@Valid MonthSettlementVO monthsettlementVO, BindingResult result, ModelMap model) {

        /*************************** 1. 接收請求參數 - 格式驗證 ************************/
        if (result.hasErrors()) {
            return "vendor-end/monthsettlement/update-MonthSettlement-Input";
        }

        /*************************** 2. 開始更新資料 **********************************/
        monthSettlementService.updateMonthSettlement(monthsettlementVO);

        /*************************** 3. 更新完成，重導至單筆資料顯示頁 *****************/
        model.addAttribute("success", "- (更新成功)");
        monthsettlementVO = monthSettlementService.getOneMonthSettlement(Integer.valueOf(monthsettlementVO.getMonthSettlementNo()));
        model.addAttribute("monthsettlementVO", monthsettlementVO);
        return "vendor-end/monthsettlement/listAllMonthSettlement";
    }

    // 刪除資料處理
    @PostMapping("delete")
    public String delete(@RequestParam("monthSettlementNo") String monthsettlementNo, ModelMap model) {

        /*************************** 1. 開始刪除資料 **********************************/
        monthSettlementService.deleteMonthSettlement(Integer.valueOf(monthsettlementNo));

        /*************************** 2. 刪除完成，重導至清單頁 ************************/
        List<MonthSettlementVO> list = monthSettlementService.getAll();
        model.addAttribute("listAllMonthSettlement", list);
        model.addAttribute("success", "- (刪除成功)");
        return "vendor-end/monthsettlement/listAllMonthSettlement";
    }

    // 條件查詢
    @PostMapping("listByCompositeQuery")
    public String listByCompositeQuery(HttpServletRequest req, Model model) {
        Map<String, String[]> map = req.getParameterMap();
        List<MonthSettlementVO> list = monthSettlementService.getAll();
        model.addAttribute("monthSettlementList", list);
        return "vendor-end/monthsettlement/listAllMonthSettlement";
    }

    // 選單資料（櫃位清單）
    @ModelAttribute("monthSettlementListData")
    protected List<MonthSettlementVO> referenceListData() {
    	List<MonthSettlementVO> list = monthSettlementService.getAll();
        return list;
    }

 // Modify referenceMapData method to match your needs
    @ModelAttribute("monthSettlementMapData")
    protected List<Map<String, Object>> referenceMapData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> option1 = new HashMap<>();
        option1.put("counterNo", 1);
        option1.put("counterName", "櫃位一");
        list.add(option1);
        // Add more options similarly
//        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
//        map.put(1, "櫃位一");
//        map.put(2, "櫃位二");
//        map.put(3, "櫃位三");
        return list;
    }

    // 去除 BindingResult 中某欄位的錯誤
    public BindingResult removeFieldError(MonthSettlementVO monthSettlementVO, BindingResult result, String removedFieldName) {
        List<FieldError> errorsToKeep = result.getFieldErrors().stream()
                .filter(fieldError -> !fieldError.getField().equals(removedFieldName))
                .collect(Collectors.toList());
        result = new BeanPropertyBindingResult(monthSettlementVO, "monthSettlementVO");
        for (FieldError fieldError : errorsToKeep) {
            result.addError(fieldError);
        }
        return result;
    }
    
   
}
