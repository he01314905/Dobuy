package com;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.counter.model.CounterService;
import com.goods.model.GoodsService;
import com.goods.model.GoodsVO;
import com.goodstype.model.GoodsTypeService;
import com.goodstype.model.GoodsTypeVO;
import com.member.model.MemberVO;

@Controller
public class FrontEndController {
	
	@Autowired
	GoodsService goodsSvc;
	@Autowired
	GoodsTypeService goodstSvc;
	@Autowired
	CounterService counterSvc;
	
	 @GetMapping("member")
	    public String getMemberPage() {
		 
	        return "front-end/normalpage/member"; // 對應 templates/content/profile.html
	    }
	 @GetMapping("home")
	    public String getHomePage() {
	        return "front-end/normalpage/homepage"; // 對應 templates/content/profile.html
	    }
	 @GetMapping("goodspage")
	    public String getgoodspagePage(Model model) {
		 List<GoodsVO> list = goodsSvc.getAll();
		 List<GoodsTypeVO> glist = goodstSvc.getAll();
		 model.addAttribute("list",list);
		 model.addAttribute("glist",glist);
	        return "front-end/normalpage/goodspage"; // 對應 templates/content/profile.html
	    }
	 
	 
	 @GetMapping("/goods/filter")
	 @ResponseBody
	 public List<GoodsVO> filterGoodsByType(@RequestParam("goodstNo") String goodstNo,
	         Model model) {
		 
		    List<GoodsVO> alist = goodsSvc.getAll();
		    int goodstNoInt ;
		    
		    try {
		    	goodstNoInt = Integer.parseInt(goodstNo); 
		    } catch (NumberFormatException e) {
    
		        return new ArrayList<>();
		    }   
		    List<GoodsVO> filteredgoodst = new ArrayList<>();
		    for (GoodsVO goods : alist) {
		        
		        if (goods.getGoodsTypeVO() != null && goods.getGoodsTypeVO().getGoodstNo() != null) {
		           
		            if (goods.getGoodsTypeVO().getGoodstNo() == goodstNoInt) {
		            	filteredgoodst.add(goods);
		            }
		        }
		    }
		    return filteredgoodst; 
		}
	 
	    @GetMapping("content/profile")
	    public String getProfilePage() {
	        return "content/profile"; // 對應 templates/content/profile.html
	    }
	    @GetMapping("content/credit")
	    public String getcreditPage() {
	        return "content/credit"; // 對應 templates/content/profile.html
	    }
	    @GetMapping("content/changeps")
	    public String getchangepsPage() {
	        return "content/changeps"; // 對應 templates/content/profile.html
	    }
	    @GetMapping("content/delete")
	    public String getdeletePage() {
	        return "content/delete"; // 對應 templates/content/profile.html
	    }
	    @GetMapping("content/add")
	    public String getaddPage() {
	        return "content/add"; // 對應 templates/content/profile.html
	    }
	    
	    
	
}