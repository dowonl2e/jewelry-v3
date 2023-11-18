package com.jewelry.main;

import com.jewelry.cms.code.model.CodeService;
import com.jewelry.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainPageController {
	
	private final CodeService codeService;
	
	@GetMapping("/main")
	public String main(ModelMap model) {
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[] {"SM"}, 2));
		model.addAttribute("nowYear", Utils.getTodayDateFormat("yyyy"));
		return "main/index";
	}
	
}
