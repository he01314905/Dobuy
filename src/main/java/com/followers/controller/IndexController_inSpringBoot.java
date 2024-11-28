package com.followers.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.followers.model.FollowersService;
import com.followers.model.FollowersVO;

//@PropertySource("classpath:application.properties") 
@Controller
public class IndexController_inSpringBoot {

    @Autowired
    FollowersService followersService;

    // inject via application.properties
    @Value("${welcome.message}")
    private String message;
    
    private List<String> myList = Arrays.asList(
        "Spring Boot Quickstart 官網 : https://start.spring.io", 
        "IDE 開發工具", 
        "直接使用(匯入)官方的 Maven Spring-Boot-demo Project + pom.xml", 
        "直接使用官方現成的 @SpringBootApplication + SpringBootServletInitializer 組態檔", 
        "依賴注入(DI) HikariDataSource (官方建議的連線池)", 
        "Thymeleaf", 
        "Java WebApp (<font color=red>快速完成 Spring Boot Web MVC</font>)"
    );
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", message);
        model.addAttribute("myList", myList);
        return "index"; //view
    }
    
    // http://......../hello?name=peter1
    @GetMapping("/hello")
    public String indexWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        model.addAttribute("message", name);
        return "index"; //view
    }
  
    //=========== 以下第63~75行是提供給 /src/main/resources/templates/back-end/emp/select_page.html 與 listAllEmp.html 要使用的資料 ===================   
    @GetMapping("/followers/selectPage")
    public String selectPage(Model model) {
        return "back-end/followers/selectPage";
    }
    
    @GetMapping("/followers/listAllFollowers")
    public String listAllFollowers(Model model) {
        return "back-end/followers/listAllFollowers";
    }
    
    @ModelAttribute("followersListData")  // for selectPage.html and listAllFollowers.html
    protected List<FollowersVO> referenceListData(Model model) {
        List<FollowersVO> list = followersService.getAll();
        return list;
    }
}
