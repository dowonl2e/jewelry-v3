package com.jewelry.sale;

import com.jewelry.cms.code.model.CodeService;
import com.jewelry.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SalePageController {

	private final CodeService codeService;
	
	@GetMapping("/list")
	public String list(ModelMap model) {
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("smlist", codeService.findAllByUpCdId("SM", 2));
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[]{"ST","SM","PT"}, 2));
		model.addAttribute("today", Utils.getTodayDateFormat("yyyy-MM-dd"));
		return "sale/sale_list";
	}
	
	@GetMapping("/popup/customer/list")
	public String customerList(@RequestParam(value = "salesno") String salesno, ModelMap model) {
		model.addAttribute("salesno", salesno);
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[] {"ST","CT"}, 2));
		return "sale/popup/sale_customer_list";
	}

	@GetMapping("/popup/date/modify")
	public String saleDataModify(@RequestParam(value = "salesno") String salesno, ModelMap model) {
		model.addAttribute("salesno", salesno);
		return "sale/popup/sale_date_modify";
	}
	
}
