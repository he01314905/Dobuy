package com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import java.util.Set;


import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.counter.model.CounterService;
import com.counter.model.CounterVO;
import com.countercarousel.model.CountercarouselService;
import com.countercarousel.model.CountercarouselVO;
import com.counterorder.model.CounterOrderService;
import com.counterorder.model.CounterOrderVO;
import com.counterorderdetail.model.CounterOrderDetailService;
import com.counterorderdetail.model.CounterOrderDetailVO;
import com.coupon.model.CouponService;
import com.coupon.model.CouponVO;
import com.goods.model.GoodsService;
import com.goods.model.GoodsVO;
import com.goodstype.model.GoodsTypeService;
import com.goodstype.model.GoodsTypeVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.storecarousel.model.StoreCarouselService;
import com.storecarousel.model.StoreCarouselVO;
import com.used.model.UsedService;
import com.used.model.UsedVO;
import com.usedpic.model.UsedPicVO;

@Controller
public class FrontEndController {

 @Autowired
 GoodsService goodsSvc;
 @Autowired
 GoodsTypeService goodstSvc;
 @Autowired
 CounterService counterSvc;
 @Autowired
 CounterOrderService counterOrderSvc;
 @Autowired
 CounterOrderDetailService counterOrderDetailSvc;
 @Autowired
 CouponService couponSvc;

 @Autowired
 CountercarouselService countercarouselSvc;
 
 
 @Autowired
 StoreCarouselService storeCarouselSvc;
 @Autowired
 MemberService memSvc;
 @Autowired
 UsedService usedSvc;

 @GetMapping("")
 public String index() {

  return "loading";
 }

 @GetMapping("member")
 public String getMemberPage(HttpSession session, Model model) {

  if (session.getAttribute("memNo") == null) {

			return "redirect:/mem/login";
		} else {
   String memNo = (String) session.getAttribute("memNo");
   List<CounterOrderVO> membersbuyorder = counterOrderSvc.ListfindByMemNoAndStatusNot4(Integer.valueOf(memNo));
//     System.out.println(membersbuyorder.size());
   if (membersbuyorder.size() == 0) {
    List<CounterVO> counterVOList = counterSvc.getAll();

    model.addAttribute("counterVOList", counterVOList);
    return "front-end/normalpage/member";
   }
   
   List<CounterOrderVO> newlist = new ArrayList<>();
   List<GoodsVO> goodsNamelist = new ArrayList<>();
   Set<GoodsVO> goodsNameSet = new HashSet<>(goodsNamelist);
   for (CounterOrderVO counterOrderVO : membersbuyorder) {
    Integer eachOrderNo = counterOrderVO.getCounterOrderNo();
    List<CounterOrderDetailVO> detailList = counterOrderDetailSvc.getDetailsByOrderNo(eachOrderNo);

//      System.out.println(detailList.size());

    for (CounterOrderDetailVO counterOrderDetailVO : detailList) {
     GoodsVO goodsVO = goodsSvc.getOneGoods(counterOrderDetailVO.getGoodsNo());
     goodsNameSet.add(goodsVO);
    }

    counterOrderVO.setCounterOrderDatailVO(detailList);
    newlist.add(counterOrderVO);
   }

   List<CouponVO> couponList = couponSvc.getAll();
   List<CounterVO> counterList = counterSvc.getAll();

   model.addAttribute("goodsNamelist", goodsNameSet);
   model.addAttribute("couponList", couponList);
   model.addAttribute("counterList", counterList);
   model.addAttribute("orders", newlist);
  }
  return "front-end/normalpage/member";
 }

	@GetMapping("home")
	public String getHomePage(Model model) {


  List<StoreCarouselVO> carousellist = storeCarouselSvc.getAll();
  List<GoodsVO> goodslist = goodsSvc.getAllGoodsStatus1();
  List<CounterVO> counterVOList = counterSvc.getAll();
  List<GoodsVO> subGoodslist = goodslist.stream().limit(10).collect(Collectors.toList());
  
  model.addAttribute("counterVOList", counterVOList);
  model.addAttribute("goodslist", subGoodslist);
  model.addAttribute("carousellist", carousellist);
  return "front-end/normalpage/homepage";
 }
// @GetMapping("/home")
// public String getHomePage(Model model) {
// 
//	// 1️⃣ 取得所有 StoreCarouselVO，並轉換圖片為 Base64
//     List<StoreCarouselVO> storeCarouselList = storeCarouselSvc.getAll();
//     Map<Integer, String> carouselImageMap = new HashMap<>();
//
//     for (StoreCarouselVO carousel : storeCarouselList) {
//         if (carousel.getCarouselPic() != null) {
//             String base64Image = Base64.getEncoder().encodeToString(carousel.getCarouselPic());
//             carouselImageMap.put(carousel.getId(), base64Image); 
//         }
//     }
//
//     // 2️⃣ 取得所有商品，並只取前 10 筆
//     List<GoodsVO> goodslist = goodsSvc.getAllGoodsStatus1();
//     List<GoodsVO> subGoodslist = goodslist.stream().limit(10).collect(Collectors.toList());
//
//     // 3️⃣ 取得所有櫃位
//     List<CounterVO> counterVOList = counterSvc.getAll();
//
//     // 4️⃣ 模型數據傳到前端
//     model.addAttribute("storeCarouselList", storeCarouselList); // 原始 StoreCarouselVO 物件
//     model.addAttribute("carouselImageMap", carouselImageMap);   // Map，包含了 StoreCarouselNo 和 base64 圖片
//     model.addAttribute("goodslist", subGoodslist);
//     model.addAttribute("counterVOList", counterVOList);
//
//     return "front-end/normalpage/homepage";
// }

 


// @GetMapping("usedgoodspage")
//     public String getusedgoodspagePage(Model model) {
//   List<UsedVO> list = usedSvc.getAll();
//   List<GoodsTypeVO> glist = goodstSvc.getAll();
//  
//   model.addAttribute("list",list);
//   model.addAttribute("glist",glist);
//         return "front-end/normalpage/usedgoodspage"; 
//     }
//
// for(
//
// CounterOrderDetailVO counterOrderDetailVO:detailList)
// {
//  GoodsVO goodsVO = goodsSvc.getOneGoods(counterOrderDetailVO.getGoodsNo());
//  goodsNamelist.add(goodsVO);
// }
//
// counterOrderVO.setCounterOrderDatailVO(detailList);newlist.add(counterOrderVO);}
//
// List<CouponVO> couponList = couponSvc.getAll();
// List<CounterVO> counterList = counterSvc.getAll();
// List<CounterVO> counterVOList = counterSvc.getAll();
//
// model.addAttribute("counterVOList",counterVOList);model.addAttribute("goodsNamelist",goodsNamelist);model.addAttribute("couponList",couponList);model.addAttribute("counterList",counterList);model.addAttribute("orders",newlist);
// }return"front-end/normalpage/member";}


 @GetMapping("goodspage")
 public String getgoodspagePage(Model model) {
  List<GoodsVO> list = goodsSvc.getgoods();
  List<GoodsTypeVO> glist = goodstSvc.getAll();
  List<CounterVO> counterVOList = counterSvc.getAll();

  model.addAttribute("counterVOList", counterVOList);
  model.addAttribute("list", list);
  model.addAttribute("glist", glist);
  return "front-end/normalpage/goodspage";
 }

 @GetMapping("usedgoodspage")
 public String getusedgoodspagePage1(Model model) {
  List<UsedVO> list = usedSvc.getUsed();
  List<GoodsTypeVO> glist = goodstSvc.getAll();
  List<CounterVO> counterVOList = counterSvc.getAll();

  model.addAttribute("counterVOList", counterVOList);
  model.addAttribute("list", list);
  model.addAttribute("glist", glist);
  return "front-end/normalpage/usedgoodspage";
 }

 @GetMapping("/goods/filter")
 @ResponseBody
 public List<GoodsVO> filterGoodsByType(@RequestParam("goodstNo") String goodstNo, Model model) {

  List<GoodsVO> alist = goodsSvc.getgoods();
  int goodstNoInt;

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

 

 @PostMapping("updatemem")
 public String updatemem(@Valid MemberVO memberVO, BindingResult result, ModelMap model, HttpSession session)
   throws IOException {
  Object memNoObj = session.getAttribute("memNo");
  Integer memNo = Integer.parseInt(memNoObj.toString());
  memSvc.findOne(memNo);
  memberVO.setMemNo(memNo);
  memSvc.updateMem(memberVO);
  memberVO = memSvc.findOne(memNo);

  model.addAttribute("memberVO", memberVO);
  return "front-end/normalpage/member";

 }

 @PostMapping("address")
 public String changeadd(@RequestParam("memAddress") String memAddress, ModelMap model, HttpSession session)
   throws IOException {

  Object memNoObj = session.getAttribute("memNo");
  Integer memNo = Integer.parseInt(memNoObj.toString());
  memSvc.upAdd(memNo, memAddress);
  MemberVO memberVO = memSvc.findOne(memNo);
  model.addAttribute("memberVO", memberVO);
  return "front-end/normalpage/member";

 }

 @PostMapping("deleteac")
 public String deleteac(ModelMap model, HttpSession session) throws IOException {
  Object memNoObj = session.getAttribute("memNo");
  Integer memNo = Integer.parseInt(memNoObj.toString());
  MemberVO memberVO = memSvc.findOne(memNo);
  memberVO.setMemStatus(0);
  memSvc.updateMem(memberVO);

  session.removeAttribute("memAccount");
  session.removeAttribute("memNo"); // 用memAccount去找memNo
  session.removeAttribute("memStatus");
  return "front-end/normalpage/member";
 }

 @PostMapping("changepas")
	public String changepas(@RequestParam("memPassword") String memPassword,
			@RequestParam("confirmPassword") String confirmPassword, ModelMap model, HttpSession session)
			throws IOException {

		Object memNoObj = session.getAttribute("memNo");
		Integer memNo = Integer.parseInt(memNoObj.toString());
		if (!memPassword.equals(confirmPassword)) {
			model.addAttribute("error", "確認密碼輸入錯誤");

		}
		memSvc.updatePass(memNo, memPassword);
		MemberVO memberVO = memSvc.findOne(memNo);
		model.addAttribute("memberVO", memberVO);
		return "front-end/normalpage/member";

	}


  @GetMapping("content/credit")
  public String getcreditPage() {
      return "content/credit"; // 對應 templates/content/profile.html
  }
  @GetMapping("content/changeps")
  public String getchangepsPage(HttpSession session,Model model) {
  	Object memNoObj = session.getAttribute("memNo");
	    Integer memNo = Integer.parseInt( memNoObj.toString());	    
	MemberVO memberVO;
	memberVO = memSvc.findOne(memNo);
	memberVO.setMemPassword("");
	model.addAttribute("memberVO",memberVO);
      return "content/changeps"; // 對應 templates/content/profile.html
  }
  @GetMapping("content/delete")
  public String getdeletePage(Model model,HttpSession session) {
  	Object memNoObj = session.getAttribute("memNo");
  	Integer memNo = Integer.parseInt( memNoObj.toString());	   
  	MemberVO memberVO;
  	memberVO = memSvc.findOne(memNo);
  	model.addAttribute("memberVO",memberVO);
      return "content/delete"; // 對應 templates/content/profile.html
  }

  @GetMapping("content/add")
  public String getaddPage(HttpSession session,Model model) {
  Object memNoObj = session.getAttribute("memNo");
	Integer memNo = Integer.parseInt( memNoObj.toString());	    
	MemberVO memberVO;
	memberVO = memSvc.findOne(memNo);
	model.addAttribute("memberVO",memberVO);
      return "content/add"; // 對應 templates/content/profile.html
  }
  @GetMapping("content/profileup")
  public String getprofileupPage(Model model,HttpSession session) {
  	Object memNoObj = session.getAttribute("memNo");
	    Integer memNo = Integer.parseInt( memNoObj.toString());	    
	MemberVO memberVO;
	memberVO = memSvc.findOne(memNo);
	model.addAttribute("memberVO",memberVO);
      return "content/profileup"; // 對應 templates/content/profile.html
  }
  @GetMapping("content/profile")
  public String getProfilePage(Model model,HttpSession session) {
  	Object memNoObj = session.getAttribute("memNo");
  	    Integer memNo = Integer.parseInt( memNoObj.toString());	    
  	MemberVO memberVO;
  	memberVO = memSvc.findOne(memNo);
  	model.addAttribute("memberVO",memberVO);
   	return "content/profile"; 
  }

}