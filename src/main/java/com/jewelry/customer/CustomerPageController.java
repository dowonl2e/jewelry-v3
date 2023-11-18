package com.jewelry.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jewelry.cms.code.model.CodeService;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerPageController {
	
	private final CodeService codeService;
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[] {"ST","CT"}, 2));
		return "customer/customer_list";
	}
	
	@GetMapping("/popup/write")
	public String writePopup(Model model) {
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("ctlist", codeService.findAllByUpCdId("CT", 2));
		return "customer/popup/customer_write";
	}

	@GetMapping("/popup/{customerno}")
	public String viewPopup(@PathVariable final Long customerno, Model model) {
		model.addAttribute("customerno", customerno);
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[] {"ST","CT"}, 2));
		return "customer/popup/customer_view";
	}
	
	@GetMapping("/popup/modify/{customerno}")
	public String modifyPopup(@PathVariable final Long customerno, Model model) {
		model.addAttribute("customerno", customerno);
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("ctlist", codeService.findAllByUpCdId("CT", 2));
		return "customer/popup/customer_modify";
	}

	@GetMapping("/popup/list")
	public String listPopup(Model model) {
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[] {"ST","CT"}, 2));
		return "customer/popup/customer_list";
	}
	
	@GetMapping("/popup/order/list/{customerno}")
	public String orderListPopup(@PathVariable("customerno") final Long customerno, ModelMap model) {
		model.addAttribute("customerno", customerno);
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("smlist", codeService.findAllByUpCdId("SM", 2));
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[]{"ST","SM","SC"}, 2));
		return "customer/popup/customer_order_list";
	}
	
	@GetMapping("/popup/order/list/{customerno}/{orderstep}")
	public String orderListPopup(@PathVariable("customerno") final Long customerno, @PathVariable("orderstep") final String orderstep, ModelMap model) {
		model.addAttribute("customerno", customerno);
		model.addAttribute("orderstep", orderstep);
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("smlist", codeService.findAllByUpCdId("SM", 2));
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[]{"ST","SM","SC"}, 2));
		return "customer/popup/customer_order_list";
	}

	@GetMapping("/popup/repair/list/{customerno}")
	public String repairListPopup(@PathVariable final Long customerno, ModelMap model) {
		model.addAttribute("customerno", customerno);
		return "customer/popup/customer_repair_list";
	}
}
