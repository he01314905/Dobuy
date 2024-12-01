package com;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.counter.model.CounterService;
import com.counter.model.CounterVO;
import com.goods.model.GoodsService;
import com.goods.model.GoodsVO;
import com.goodstype.model.GoodsTypeService;
import com.goodstype.model.GoodsTypeVO;

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
		 List<CounterVO> glist = counterSvc.getAll();
		 model.addAttribute("list",list);
		 model.addAttribute("glist",glist);
	        return "front-end/normalpage/goodspage"; // 對應 templates/content/profile.html
	    }
	 
	 
	 @GetMapping("/goods/filter")
	 @ResponseBody
	 public List<GoodsVO> filterGoodsByType(@RequestParam("counterNo") String counterNo,
	         Model model) {
		 System.out.println("接收到的 counterNo: " + counterNo);
		    List<GoodsVO> alist = goodsSvc.getAll();
		    int counterNoInt ;
		    try {
		    	counterNoInt = Integer.parseInt(counterNo); // 將 String 轉換為 int
		    } catch (NumberFormatException e) {
		        System.out.println("轉換 counterNo 失敗: " + counterNo);
		        // 根據需求處理錯誤，這裡可以返回空列表或拋出異常
		        return new ArrayList<>();
		    }   
		    List<GoodsVO> filteredcounter = new ArrayList<>();
		    for (GoodsVO goods : alist) {
		        // 確保 goods.getGoodsTypeVO() 和 getGoodstNo() 不是 null
		        if (goods.getCounterVO() != null && goods.getCounterVO().getCounterNo() != null) {
		            // 直接比較 int 類型的數值
		            if (goods.getCounterVO().getCounterNo() == counterNoInt) {
		            	filteredcounter.add(goods);
		            }
		        }
		    }
		    
		    System.out.println("過濾後的商品資料: " + filteredcounter);
		    return filteredcounter; // 返回過濾後的結果
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