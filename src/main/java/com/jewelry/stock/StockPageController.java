package com.jewelry.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jewelry.cms.code.model.CodeService;
import com.jewelry.stock.model.StockService;

@Controller
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockPageController {

	private final CodeService codeService;
	
	private final StockService stockService;

	@GetMapping("/list")
	public String list(ModelMap model) {
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("smlist", codeService.findAllByUpCdId("SM", 2));
		model.addAttribute("oclist", codeService.findAllByUpCdId("OC", 2));
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[]{"ST","SM","SC","OC"}, 2));
		return "stock/stock_list";
	}

	@GetMapping("/popup/write")
	public String stockWritePopup(ModelMap model) {
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("smlist", codeService.findAllByUpCdId("SM", 2));
		model.addAttribute("sclist", codeService.findAllByUpCdId("SC", 2));
		model.addAttribute("oclist", codeService.findAllByUpCdId("OC", 2, "ALL"));
		model.addAttribute("prevStockList", stockService.findAllPrevStocks());
		return "stock/popup/stock_write";
	}

	@GetMapping("/popup/{stockno}")
	public String stockViewPopup(@PathVariable final Long stockno, ModelMap model) {
		model.addAttribute("stockno", stockno);
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[]{"ST","SM","SC","OC"}, 2));
		return "stock/popup/stock_view";
	}

	@GetMapping("/popup/modify/{stockno}")
	public String stockModifyPopup(@PathVariable final Long stockno, ModelMap model) {
		model.addAttribute("stockno", stockno);
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("smlist", codeService.findAllByUpCdId("SM", 2));
		model.addAttribute("sclist", codeService.findAllByUpCdId("SC", 2));
		model.addAttribute("oclist", codeService.findAllByUpCdId("OC", 2, "ALL"));
		model.addAttribute("prevStockList", stockService.findAllPrevStocks());
		return "stock/popup/stock_modify";
	}
	
	@GetMapping("/accumulation/list")
	public String listAll(ModelMap model) {
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("smlist", codeService.findAllByUpCdId("SM", 2));
		model.addAttribute("oclist", codeService.findAllByUpCdId("OC", 2));
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[]{"ST","SM","SC","OC"}, 2));
		return "stock/stock_accumulation_list";
	}

	@GetMapping("/popup/reg-date/modify")
	public String stockRegDateModifyPopup(@RequestParam(value = "stocksno") String stocksno, ModelMap model) {
		model.addAttribute("stocksno", stocksno);
		return "stock/popup/stock_regdate_modify";
	}

	@GetMapping("/popup/type/modify")
	public String stockTypeModifyPopup(@RequestParam(value = "stocksno") String stocksno, ModelMap model) {
		model.addAttribute("stocksno", stocksno);
		model.addAttribute("oclist", codeService.findAllByUpCdId("OC", 2));
		return "stock/popup/stock_type_modify";
	}
	
	@GetMapping("/popup/vender/modify")
	public String venderModifyPopup(@RequestParam(value = "stocksno") String stocksno, ModelMap model) {
		model.addAttribute("stocksno", stocksno);
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[] {"MT","VT"}, 2));
		return "stock/popup/stock_vender_modify";
	}
	
	@GetMapping("/popup/customer/order")
	public String stockCustomerOrderPopup(@RequestParam(value = "stocksno") String stocksno, ModelMap model) {
		model.addAttribute("stocksno", stocksno);
		model.addAttribute("cdmapper", codeService.findAllByUpCdId("CT", 2));
		return "stock/popup/stock_customer_order_modify";
	}	

	@GetMapping("/popup/sale")
	public String stockSalePopup(@RequestParam(value = "stocksno") String stocksno, ModelMap model) {
		model.addAttribute("stocksno", stocksno);
		model.addAttribute("ptlist", codeService.findAllByUpCdId("PT", 2));
		return "stock/popup/stock_sale_write";
	}
	
}
