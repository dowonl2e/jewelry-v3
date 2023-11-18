package com.jewelry;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

	@GetMapping("/error")
	public String error404() {
		return "error/404";
	}
	
	@GetMapping("/error/access-denied")
	public String errorAccessDenied() {
		return "error/access_denied";
	}
	
}
