package com.used.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.used.model.UsedService;
import com.used.model.UsedVO;
import com.usedpic.model.UsedPicService;
import com.usedpic.model.UsedPicVO;

//Chapt2-65 ch3-77 ch8-139
@Controller
@RequestMapping("/used")
public class UsedController {
	
	@Autowired
	UsedService usedSvc;

	@Autowired
	UsedPicService usedPicSvc;
	
	@Autowired
	EntityManager EntityManager;
	
	/*
	 * This method will serve as addUsed.html handler.
	 */
	@GetMapping("/addUsed")
	public String addUsed(ModelMap model) {
		UsedVO usedVO = new UsedVO();
		model.addAttribute("usedVO", usedVO);
		return "front-end/used/addUsed";
	}
	
	@PostMapping("/getOneUsed")
	public String getOneUsed( @RequestParam("usedNo") String usedNo, Model model) {
		 
		UsedVO usedVO = usedSvc.getOneUsed(Integer.valueOf(usedNo));
		model.addAttribute("usedVO", usedVO);
		return "front-end/used/listOneUsed";
	}
	 
	
	@PostMapping("/getOneUsedTest")
	public String getOneUsedTest( @RequestParam("usedNo") String usedNo, Model model) {
		
		UsedVO usedVO = usedSvc.getOneUsed(Integer.valueOf(usedNo));
		model.addAttribute("usedVO", usedVO);
		return "front-end/used/shop_detail_used";
	}

	/*
	 * This method will be called on addUsed.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("/insert")
	public String insert(
	        @Valid UsedVO usedVO,
	        BindingResult result,
	        ModelMap model,
	        @RequestParam("upfiles") MultipartFile[] parts,
	        RedirectAttributes redirectAttributes) throws IOException {

		List<MultipartFile> validPictures=filterEmptyFiles(parts);

	    if (result.hasErrors() || validPictures.isEmpty()) {
//	    	System.out.println(result.getFieldErrorCount());
//	    	System.out.println(result.getFieldError());
	        model.addAttribute("errorMessage", "請輸入正確的商品資訊，並上傳至少一張照片");
	        return "front-end/used/addUsed";
	    }

	    // 轉換 MultipartFile 到 UsedPicVO
	    List<UsedPicVO> usedPicsList = new ArrayList<>();
	    for (MultipartFile multipartFile : validPictures) {
	        UsedPicVO usedPicVO = new UsedPicVO();
	        usedPicVO.setUsedPicName(multipartFile.getOriginalFilename());
	        usedPicVO.setUsedPics(multipartFile.getBytes());
	        usedPicsList.add(usedPicVO);
	    }

	    // 保存資料
	    usedVO = usedSvc.saveUsedWithPics(usedVO, usedPicsList);
//	    System.out.println(usedNo);
//	    usedVO = usedSvc.getOneUsed(usedNo);
//	    System.out.println(usedVO.getUsedPics().isEmpty());
	    model.addAttribute("usedVO",usedVO);
	    redirectAttributes.addFlashAttribute("success", "新增成功！");
	    return "front-end/used/listOneUsed";
	}


	
	

	/*
	 * This method will be called on update_Used_input.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("/update")
	public String update(@Valid UsedVO usedVO, BindingResult result, ModelMap model,
			@RequestParam("upfiles") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		if (result.hasErrors()) {
//			System.out.println(result.getFieldError());
				List<UsedPicVO> usedPics= usedPicSvc.findAllPicsByUsedNo(usedVO.getUsedNo());
				usedVO.setUsedPics(usedPics);
				return "front-end/used/update_used_input";
			}
		List<MultipartFile> validPictures=filterEmptyFiles(parts);
		
		if (validPictures.isEmpty()) { // 使用者未選擇要上傳的新圖片時 
			//檢查資料庫有無照片 若無就返回 並提示警告
			if((usedPicSvc.findAllPicsByUsedNo(usedVO.getUsedNo()).size())==0) {
				model.addAttribute("errorMessage", "商品資料查無照片  請至少上傳一張照片");
				return "front-end/used/update_used_input";
			}
//			else {
//			//檢查資料庫照片 若有就提取舊照片
//			List<UsedPicVO> oldUsedPics = usedSvc.getOneUsed(usedVO.getUsedNo()).getUsedPics();
//			usedVO.setUsedPics(oldUsedPics);
//			}
			
		} else {
			//將新增的圖片跑迴圈後塞入list以便轉交
			List<UsedPicVO> usedPicsList = new ArrayList<>();
		    for (MultipartFile multipartFile : validPictures) {
		        UsedPicVO usedPicVO = new UsedPicVO();
		        usedPicVO.setUsedVO(usedVO);
		        usedPicVO.setUsedPicName(multipartFile.getOriginalFilename());
		        usedPicVO.setUsedPics(multipartFile.getBytes());
		        usedPicsList.add(usedPicVO);
		    }
			usedVO.setUsedPics(usedPicsList);
		}
		
		
		/*************************** 2.開始修改資料 *****************************************/
	
		Integer usedNo=usedSvc.updateUsed(usedVO);
		
		
		EntityManager.clear();
		
		UsedVO newUsedVO= usedSvc.getOneUsed(usedNo);
//		System.out.println(newUsedVO.getUsedPics().size());//debug用 緩存問題(以解決)
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		model.addAttribute("usedVO", newUsedVO);
		return "front-end/used/listOneUsed"; // 修改成功後轉交listOneUsed.html
	}

	/*
	 * This method will be called on listAllUsed.html form submission, handling POST request
	 */
	@PostMapping("/delete")
	public String delete(@RequestParam("usedNo") String usedNo, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// UsedService UsedSvc = new UsedService();
		usedSvc.deleteUsed(Integer.valueOf(usedNo));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<UsedVO> list = usedSvc.getAll();
		model.addAttribute("usedListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "front-end/used/listAllUsed"; // 刪除完成後轉交listAllUsed.html
	}

	/*
	 * 第一種作法 Method used to populate the List Data in view. 如 : 
	 * <form:select path="usedPicno" id="usedPicno" items="${usedPicListData}" itemValue="usedPicno" itemLabel="dname" />
	 */
//	@ModelAttribute("usedPicListData")
//	protected List<UsedPicVO> referenceListData() {
//		// usedPicService usedPicSvc = new usedPicService();
//		List<UsedPicVO> list = usedPicSvc.getAll();
//		return list;
//	}

	/*
	 * 【 第二種作法 】 Method used to populate the Map Data in view. 如 : 
	 * <form:select path="usedPicno" id="usedPicno" items="${depMapData}" />
	 */
//	@ModelAttribute("usedPicMapData") //
//	protected Map<Integer, String> referenceMapData() {
//		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
//		map.put(10, "財務部");
//		map.put(20, "研發部");
//		map.put(30, "業務部");
//		map.put(40, "生管部");
//		return map;
//	}
//
	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(UsedVO usedVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(usedVO, "usedVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
	
	
	
	//檢查上傳照片資料是否為空
	public List<MultipartFile> filterEmptyFiles(MultipartFile[] parts) {
	    List<MultipartFile> uploadedFiles = new ArrayList<>();
	    
	    for (MultipartFile file : parts) {
	        if (!file.isEmpty()) {
	            uploadedFiles.add(file);  // 只將非空檔案加入列表
	        }
	    }

	    // 回傳 list 可以進行後續處理
		return uploadedFiles;
	}

}
