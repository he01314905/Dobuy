package com;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.member.model.MemberService;
import com.member.model.MemberVO;

@Controller
@RequestMapping("/back-end")
public class BackEndController {
	@Autowired
	MemberService memberSvc;
	@GetMapping("member")
	public String member(ModelMap model) {
		List<MemberVO> memlist = memberSvc.getAll();
		model.addAttribute("memlist",memlist);
		return "back-end/member/member";
	}
	
	@PostMapping("updateStatus")
	public String updateMemberStatus(@RequestParam("memStatus") Integer memStatus,
	                                  @RequestParam("memNo") Integer memNo) {
	
		    memberSvc.upStatus(memNo, memStatus);

	    return "redirect:/back-end/member"; 
	}

}