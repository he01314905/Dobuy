package com.manager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.auth.model.AuthService;
import com.auth.model.AuthVO;
import com.manager.model.ManagerService;
import com.manager.model.ManagerVO;
import com.managerauth.model.ManagerAuthService;
import com.managerauth.model.ManagerAuthVO;



//@PropertySource("classpath:application.properties") // 於https://start.spring.io建立Spring Boot專案時, application.properties文件預設已經放在我們的src/main/resources 目錄中，它會被自動檢測到
@Controller
public class ManagerAuthController {
	
	// @Autowired (●自動裝配)(Spring ORM 課程)
	// 目前自動裝配了EmpService --> 供第66使用
	@Autowired
	ManagerService managerSvc;
	
	@Autowired
	AuthService authSvc;
	@Autowired
	ManagerAuthService managerAuthSvc;
	
    // inject(注入資料) via application.properties
   
  
    //=========== 以下第63~75行是提供給 /src/main/resources/templates/back-end/emp/select_page.html 與 listAllEmp.html 要使用的資料 ===================   
    @GetMapping("/manager/select_page")
	public String select_page(Model model) {
		return "back-end/manager/select_page";
	}
//    @GetMapping("select_page")
//   	public String select_page(ModelMap model) {
//   		return "back-end/manager/select_page";
//   	}
    
    @GetMapping("/manager/listAllManager")
	public String listAllManager(Model model ,HttpServletRequest request) {
    	List<ManagerAuthVO> allAuth = managerAuthSvc.getAll();
    	HttpSession session = request.getSession();
    	 List<ManagerAuthVO> managerAuthList = (List<ManagerAuthVO>) session.getAttribute("auth");
    	  Integer loggedInManagerNo = (Integer) session.getAttribute("managerNo");
    	  
    	    boolean isAdmin = false; // 假設 "1" 表示超級管理員
    	    for (ManagerAuthVO managerAuth : managerAuthList) {
    	    	
    	        if (managerAuth.getManagerNo().getManagerNo().equals(loggedInManagerNo)) {
    	        
    	                if ("超級管理員".equals(managerAuth.getAuthNo().getAuthTitle())) { // 假設 "1" 是超級管理員的權限代碼
    	                    isAdmin = true;
    	                    
    	                    break;
    	                }
    	            }
    	            
    	        }
    	 
    	    model.addAttribute("isAdmin", isAdmin);
    	model.addAttribute("allAuth",allAuth);
		return "back-end/manager/listAllManager";
    }
    @GetMapping("/manager/listAllAuth")
   	public String listAllAuth(Model model,HttpServletRequest request) {
    	HttpSession session = request.getSession();
   	 List<ManagerAuthVO> managerAuthList = (List<ManagerAuthVO>) session.getAttribute("auth");
   	  Integer loggedInManagerNo = (Integer) session.getAttribute("managerNo");
   	  
   	    boolean isAdmin = false; // 假設 "1" 表示超級管理員
   	    for (ManagerAuthVO managerAuth : managerAuthList) {
   	    	
   	        if (managerAuth.getManagerNo().getManagerNo().equals(loggedInManagerNo)) {
   	        
   	                if ("超級管理員".equals(managerAuth.getAuthNo().getAuthTitle())) { // 假設 "1" 是超級管理員的權限代碼
   	                    isAdmin = true;
   	                    
   	                    break;
   	                }
   	            }
   	            
   	        }
   	  System.out.println(isAdmin);
   	    model.addAttribute("isAdmin", isAdmin);
   		return "back-end/manager/listAllAuth";
   	}
       
    
    @ModelAttribute("managerListData")  // for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
	protected List<ManagerVO> referenceListData(Model model) {
    	List<ManagerVO> list = managerSvc.getAll();
		return list;
	}
    @ModelAttribute("authListData")  // for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
   	protected List<AuthVO> referenceListData1(Model model) {
   		
       	List<AuthVO> list = authSvc.getAll();
   		return list;
   	}
    
//	@ModelAttribute("deptListData") // for select_page.html 第135行用
//	protected List<DeptVO> referenceListData_Dept(Model model) {
//		model.addAttribute("deptVO", new DeptVO()); // for select_page.html 第133行用
//		List<DeptVO> list = deptSvc.getAll();
//		return list;
//	}

}