package com.root.controller35;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.countercarousel.model.CountercarouselVO;
import com.followers.model.FollowersVO;
import com.goods.model.GoodsVO;
import com.monthsettlement.model.MonthSettlementVO;
import com.product35.model.ProductVO35;
import com.storecarousel.model.storeCarouselVO;

@Controller
public class RootController35 {

	@GetMapping("/")
	public String redirectToHomepage(ModelMap model) {
		model.addAttribute("goodsVO", new GoodsVO());
		return "/back-end/homePage35";
	}

	@GetMapping("addCarousel_page")
	public String redirecToAddCarousel(ModelMap model) {
		model.addAttribute("countercarouselVO", new CountercarouselVO()); // 创建一个空的 CountercarouselVO 对象
		return "vendor-end/front-end-carousel/addCarousel";
	}

	@GetMapping("addProduct_page")
	public String redirectToAddProduct(ModelMap model) {
		model.addAttribute("productVO", new ProductVO35()); // 创建一个空的 ProductVO 对象
		return "vendor-end/front-end-product/addProduct";
	}

	@GetMapping("shop-detail")
	public String redirectToShopDetail(ModelMap model) {
		model.addAttribute("goodsVO", new GoodsVO()); // 创建一个空的 goods 对象
		return "front-end/shop-detail/shop-detail";
	}

	@GetMapping("listAllMonthSettlement")
	public String redirectTolistAllMonthSettlement(ModelMap model) {
		model.addAttribute("MonthSettlementVO", new MonthSettlementVO()); // 创建一个空的 MonthSettlement 对象
		return "vendor-end/monthsettlement/listAllMonthSettlement";
	}
	
	@GetMapping("selectPageStorecarousel")
	public String redirectToselectPageStorecarousel(ModelMap model) {
		model.addAttribute("storeCarouselVO", new storeCarouselVO()); // 创建一个空的 MonthSettlement 对象
		return "back-end/storecarousel/selectPageStorecarousel";
	}
	
	@GetMapping("addMonthSettlement")
	public String redirectToaddMonthSettlement(ModelMap model) {
		model.addAttribute("MonthSettlementVO", new MonthSettlementVO()); // 创建一个空的 MonthSettlement 对象
		return "vendor-end/monthsettlement/addMonthSettlement";
	}
	
	@GetMapping("listAllFollowers")
	public String redirectTolistAllFollowers(ModelMap model) {
		model.addAttribute("FollowersVO", new FollowersVO()); // 创建一个空的 MonthSettlement 对象
		return "vendor-end/followers/listAllFollowers";
	}
	
	@GetMapping("listAllStorecarousel")
	public String redirectTolistAllStorecarousel(ModelMap model) {
		model.addAttribute("storeCarouselVO", new storeCarouselVO()); // 创建一个空的 MonthSettlement 对象
		return "back-end/storecarousel/listAllStorecarousel";
	}
	
	@GetMapping("selectPage")
	public String redirectToselectPage(ModelMap model) {
		model.addAttribute("MonthSettlementVO", new MonthSettlementVO()); // 创建一个空的 MonthSettlement 对象
		return "vendor-end/monthsettlement/selectPage";
	}
	

}
