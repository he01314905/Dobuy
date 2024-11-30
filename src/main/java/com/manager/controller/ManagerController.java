package com.manager.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.auth.model.AuthService;
import com.auth.model.AuthVO;
import com.manager.model.ManagerService;
import com.manager.model.ManagerVO;
import com.managerauth.model.ManagerAuthService;
import com.managerauth.model.ManagerAuthVO;

@Controller
@RequestMapping("/manager")
public class ManagerController {

	
	
	@Autowired
	ManagerService managerSvc;
	@Autowired
	ManagerAuthService managerAuthSvc;
	@Autowired
	AuthService authSvc;
	
	
	
	
	
	@GetMapping("addManager")
	public String addManager(ModelMap model) {
		ManagerVO managerVO = new ManagerVO();
		managerVO.setManagerstatus(1);
		model.addAttribute("managerVO", managerVO);
		return "back-end/manager/addManager";
	}
	@PostMapping("insert")
	public String insert(@Valid ManagerVO managerVO, BindingResult result, ModelMap model)
			 throws IOException {
		// EmpService empSvc = new EmpService();
		 boolean accountExists = managerSvc.isAccountExist(managerVO.getManagerAccount());
		    
		    if (accountExists) {
		        // 如果帳號已存在，則加入錯誤訊息並返回新增頁面
		        result.rejectValue("managerAccount", "account.exists", "此帳號已經存在，請選擇其他帳號！");
		        return "back-end/manager/addManager";
		    }
		managerSvc.addManager(managerVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<ManagerVO> list = managerSvc.getAll();
		model.addAttribute("managerListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/manager/listAllManager"; // 新增成功後重導至IndexController_inSpringBoot.java的第58行@GetMapping("/emp/listAllEmp")
	}
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("managerNo") String managerNo, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		ManagerVO managerVO = managerSvc.getOneManager(Integer.valueOf(managerNo));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("managerVO", managerVO);
		return "back-end/manager/update_manager_input"; // 查詢完成後轉交update_emp_input.html
	}
	@PostMapping("update")
	public String update(@Valid ManagerVO managerVO, BindingResult result, ModelMap model) 
			throws IOException {
		managerSvc.updateManager(managerVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		managerVO = managerSvc.getOneManager(Integer.valueOf(managerVO.getManagerNo()));
		model.addAttribute("managerVO", managerVO);
		return "back-end/manager/listOneManager";
}
	
	@PostMapping("delete")
	public String delete(@RequestParam("managerNo") String managerNo, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// EmpService empSvc = new EmpService();
		managerSvc.deleteManager(Integer.valueOf(managerNo));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<ManagerVO> list = managerSvc.getAll();
		model.addAttribute("managerListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/manager/listAllManager"; // 刪除完成後轉交listAllEmp.html
	}
	
	
	
	@PostMapping("updateAuth")
	public String updateAuth( @RequestParam("authTypes") List<Integer> authNo,
            @RequestParam("managerNo") String managerNo, 
            ModelMap model) {
		List<ManagerAuthVO> existingAuths = managerAuthSvc.getOne(Integer.valueOf(managerNo));

	    
	    for (ManagerAuthVO existingAuth : existingAuths) {
	    	  Integer existingAuthNo = existingAuth.getAuthNo().getAuthNo();
	        if (!authNo.contains(existingAuthNo)) {
	            managerAuthSvc.deleteAuthByManager(Integer.valueOf(managerNo),existingAuthNo);
	        }
	    }
		for(Integer authNos: authNo) {
			ManagerAuthVO managerAuthVO = new ManagerAuthVO();
		AuthVO av=authSvc.getOneAuth(authNos);
		managerAuthVO.setAuthNo(av);
		ManagerVO mv = managerSvc.getOneManager(Integer.valueOf(managerNo));
		managerAuthVO.setManagerNo(mv);
		 managerAuthSvc.updateAuth(managerAuthVO);
		}
		List<ManagerVO> sb= managerSvc.getAll();
		
		model.addAttribute("managerListData",sb);
		
		return "redirect:/manager/listAllManager";
}

	@PostMapping("getOneAuth")
	public String getOneAuth(@RequestParam("managerNo") String managerNo, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		List<AuthVO> authList = authSvc.getAll();
		
		List<ManagerAuthVO> list =  managerAuthSvc.getOne(Integer.valueOf(managerNo));	 
		List<Integer> authNoList= new ArrayList();
		for(AuthVO AuthVO:authList) {
			AuthVO.getAuthNo();
		}
		
		for(ManagerAuthVO managerAuthVO:list) {
			AuthVO VO = managerAuthVO.getAuthNo();
			Integer authNo=VO.getAuthNo();
			authNoList.add(authNo);
			
		}
		
		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("authList",authList);
		model.addAttribute("List", authNoList);
		model.addAttribute("managerNo",managerNo);
		return "back-end/manager/updateAuth"; 
	
}
}

