package com.jewelry.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	@GetMapping("/signin")
	public String signin() {
		return "user/signin";
	}
	
	@GetMapping("/signup")
	public String signup() throws Exception {
		return "user/signin";
	}
	
}
