package com.storecarousel.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.storecarousel.model.storecarouselRepository;
import com.storecarousel.model.storeCarouselVO;

@Controller
@RequestMapping("/storecarousel")
public class StorecarouselController {

    @Autowired
    storecarouselRepository storeCarouselRepository;

    @GetMapping("/add")
    public String showRegisterPage(Model model) {
        model.addAttribute("storeCarouselVO", new storeCarouselVO());
        return "back-end/storecarousel/storecarousetest";
    }

    @PostMapping("insert")
    public String insert(@ModelAttribute storeCarouselVO storeCarouselVO) {
        try {
            MultipartFile file = storeCarouselVO.getUpFile();
            storeCarouselVO.setCarouselPic(file.getBytes()); // 將字節數據存儲到持久化字段
            storeCarouselRepository.save(storeCarouselVO);
            System.out.println("File uploaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return "back-end/storecarousel/storecarousetest";
    }

    // 顯示圖片列表
    @GetMapping("listAllStorecarousel")
    public String getAllImages(ModelMap model) {
        List<storeCarouselVO> carouselList = storeCarouselRepository.findAll();
        for (storeCarouselVO i : carouselList) {
            i.convertToBase64();
        }

        model.addAttribute("carouselList", carouselList);
        return "/back-end/storecarousetest";
    }
}
