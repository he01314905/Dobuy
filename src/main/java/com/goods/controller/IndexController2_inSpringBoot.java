package com.goods.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.counter.model.CounterService;
import com.counter.model.CounterVO;
import com.goods.model.GoodsService;
import com.goods.model.GoodsVO;
import com.msg.model.MsgService;

//@PropertySource("classpath:application.properties") // 於https://start.spring.io建立Spring Boot專案時, application.properties文件預設已經放在我們的src/main/resources 目錄中，它會被自動檢測到
@Controller
public class IndexController2_inSpringBoot {

	// @Autowired (●自動裝配)(Spring ORM 課程)
	// 目前自動裝配了EmpService --> 供第66使用
	@Autowired
	GoodsService goodsSvc;
	@Autowired
	CounterService counterSvc;
    @Autowired
    MsgService msgSvc;

	// inject(注入資料) via application.properties
	@Value("${welcome.message}")
	private String message;

	private List<String> myList = Arrays.asList("Spring Boot Quickstart 官網 : https://start.spring.io", "IDE 開發工具",
			"直接使用(匯入)官方的 Maven Spring-Boot-demo Project + pom.xml",
			"直接使用官方現成的 @SpringBootApplication + SpringBootServletInitializer 組態檔",
			"依賴注入(DI) HikariDataSource (官方建議的連線池)", "Thymeleaf",
			"Java WebApp (<font color=red>快速完成 Spring Boot Web MVC</font>)");

	@GetMapping("/goods")
	public String index(Model model) {
		model.addAttribute("message", message);
		model.addAttribute("myList", myList);
		return "vendor-end/goods/goods"; // view
	}

	// http://......../hello?name=peter1
	@GetMapping("/hello2")
	public String indexWithParam(@RequestParam(name = "name", required = false, defaultValue = "") String name,
			Model model) {
		model.addAttribute("message", name);
		return "goods"; // view
	}

	// =========== 以下第63~75行是提供給
	// /src/main/resources/templates/back-end/emp/select_page.html 與 listAllEmp.html
	// 要使用的資料 ===================
	@GetMapping("/goods/select_page")
	public String select_page(Model model) {
		return "vendor-end/goods/select_page";
	}

	@GetMapping("/goods/listAllGoods")
	public String listAllGoods(Model model) {
		return "vendor-end/goods/listAllGoods";
	}
	
	@GetMapping("/goods/listAllCheckStatus")
	public String listAllCheckStatus(Model model,HttpSession session) {
		if(session.getAttribute("managerNo")==null) {
			return "redirect:/login/Login";
		}
	
		return "vendor-end/goods/listAllCheckStatus";
	}
	
	@GetMapping("/goods/listAllCounterGoods")
	public String listAllCounterGoods(HttpSession session ,Model model) {
		//登錄時檢查counter
         CounterVO counter = (CounterVO) session.getAttribute("counter");
	        if (counter != null) {
	        	 if (counter.getCounterStatus() <= 2) {
	                 return "redirect:/counter/Counterindex"; // 如果 counterStatus 為 2，則重定向
	             }
	        	 model.addAttribute("counter", counterSvc.getOneCounter(counter.getCounterNo()));
	        	 model.addAttribute("msgSvc", msgSvc);
	        	return "vendor-end/goods/listAllCounterGoods";
	        } else {
	            return "redirect:/counter/login";
	        }	

	}

	@ModelAttribute("goodsListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
	protected List<GoodsVO> referenceListData(Model model) {
		List<GoodsVO> list = goodsSvc.getAll();
		return list;
	}
	
	//任國測試櫃位管理商品
	@ModelAttribute("CountergoodsListData") 
	protected List<GoodsVO> CounterreferenceListData(HttpSession session, Model model) {
	    CounterVO counter = (CounterVO) session.getAttribute("counter");
	    if (counter != null) {
	        List<GoodsVO> list = goodsSvc.getOneCounter1(counter.getCounterNo());
	        return list;
	    } else {
	        // 如果counter為null，返回一個空列表或處理錯誤
	        model.addAttribute("error", "未登錄或Session信息遺失");
	        return new ArrayList<>(); // 或者其他適當的錯誤處理
	    }
	}

}