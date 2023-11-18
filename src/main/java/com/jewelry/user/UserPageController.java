package com.jewelry.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserPageController {
	
	@GetMapping("/list")
	public String list() {
		return "user/user_list";
	}

	@GetMapping("/write")
	public String write() {
		return "user/user_write";
	}

	@GetMapping("/{userid}")
	public String view(@PathVariable final String userid, ModelMap model) {
		model.addAttribute("userid", userid);
		return "user/user_view";
	}

	@GetMapping("/modify/{userid}")
	public String modify(@PathVariable final String userid, ModelMap model) {
		model.addAttribute("userid", userid);
		return "user/user_modify";
	}
	
}
