package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import com.used.model.UsedService;
import com.used.model.UsedVO;
import java.util.*;



//@PropertySource("classpath:application.properties") // 於https://start.spring.io建立Spring Boot專案時, application.properties文件預設已經放在我們的src/main/resources 目錄中，它會被自動檢測到
@Controller
public class UsedIndexController_inSpringBoot {
	
	// @Autowired (●自動裝配)(Spring ORM 課程)
	// 目前自動裝配了EmpService --> 供第60使用
	@Autowired
	UsedService usedSvc;
	
    // inject(注入資料) via application.properties
    @Value("${welcome.message}")
    private String message;
	
    private List<String> myList = Arrays.asList("Spring Boot Quickstart 官網 : https://start.spring.io", "IDE 開發工具", "直接使用(匯入)官方的 Maven Spring-Boot-demo Project + pom.xml", "直接使用官方現成的 @SpringBootApplication + SpringBootServletInitializer 組態檔", "依賴注入(DI) HikariDataSource (官方建議的連線池)", "Thymeleaf", "Java WebApp (<font color=red>快速完成 Spring Boot Web MVC</font>)");
    @GetMapping("/used")
    public String index(Model model) {
    	model.addAttribute("message", message);
        model.addAttribute("myList", myList);
        return "usedindex"; //view
    }
    
  
    //=========== 以下第57~62行是提供給 /src/main/resources/templates/front-end/emp/select_page.html 與 listAllEmp.html 要使用的資料 ===================   
    @GetMapping("/used/select_page")
	public String select_page(Model model) {
		return "front-end/used/select_page";
	}
    @GetMapping("/used/member")
	public String member(Model model) {
		return "front-end/used/member";
	}
    
    @GetMapping("/used/listAllUsed")
	public String listAllUsed(Model model) {
		return "front-end/used/listAllUsed";
	}
    
    @ModelAttribute("usedListData")  // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<UsedVO> referenceListData(Model model) {
		
    	List<UsedVO> list = usedSvc.getAll();
		return list;
	}

}