package com.jewelry.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberPageController {

	@Autowired
	private HttpSession session;
	
	@GetMapping("/profile")
	public String profile(ModelMap model) {
		return "member/profile/profile_view";
	}
	
	@GetMapping("/profile/modify")
	public String profileModify(ModelMap model) {
		return "member/profile/profile_modify";
	}
}
