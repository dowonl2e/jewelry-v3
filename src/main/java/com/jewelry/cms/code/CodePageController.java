package com.jewelry.cms.code;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/code")
public class CodePageController {
	
	@GetMapping("/list")
	public String list() {
		return "cms/code/code_list";
	}
	
	@GetMapping("/write")
	public String write() {
		return "cms/code/code_write";
	}
	
	@GetMapping("/modify/{cdid}")
	public String modify(@PathVariable final String cdid, Model model){
		model.addAttribute("cdid", cdid);
		return "cms/code/code_modify";
	}
	
	@GetMapping("/popup/list/{upcdid}/{cddepth}")
	public String listPopup(@PathVariable("upcdid") final String upcdid, @PathVariable("cddepth") final Integer cddepth, Model model) {
		model.addAttribute("upcdid", upcdid);
		model.addAttribute("cddepth", cddepth);
		return "cms/code/popup/code_list";
	}

	@GetMapping("/popup/write/{upcdid}/{cddepth}")
	public String writePopup(@PathVariable("upcdid") final String upcdid, @PathVariable("cddepth") final Integer cddepth, Model model) {
		model.addAttribute("upcdid", upcdid);
		model.addAttribute("cddepth", cddepth);
		return "cms/code/popup/code_write";
	}

	@GetMapping("/popup/modify/{cdid}/{cddepth}")
	public String modifyPopup(@PathVariable("cdid") final String cdid, @PathVariable("cddepth") final Integer cddepth, Model model){
		model.addAttribute("cdid", cdid);
		model.addAttribute("cddepth", cddepth);
		return "cms/code/popup/code_modify";
	}
}
