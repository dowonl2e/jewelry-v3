package com.jewelry.order;

import com.jewelry.cms.code.model.CodeService;
import com.jewelry.stock.model.StockService;
import com.jewelry.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderPageController {

	private final CodeService codeService;
	
	private final StockService stockService;
	
	@GetMapping("/list")
	public String list(ModelMap model) {
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("smlist", codeService.findAllByUpCdId("SM", 2));
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[]{"ST","SM","SC"}, 2));
		return "order/order_list";
	}
	
	//고객 제품
	@GetMapping("/popup/customer/write")
	public String writeCustomerPopup(ModelMap model) {
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("smlist", codeService.findAllByUpCdId("SM", 2));
		model.addAttribute("sclist", codeService.findAllByUpCdId("SC", 2));
		model.addAttribute("today", Utils.getTodayDateFormat("yyyy-MM-dd"));
		return "order/popup/customer/order_write";
	}

	@GetMapping("/popup/customer/{orderno}")
	public String viewCustomerPopup(@PathVariable final Long orderno, ModelMap model) {
		model.addAttribute("orderno", orderno);
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[] {"ST","SM","SC"}, 2));
		return "order/popup/customer/order_view";
	}
	
	@GetMapping("/popup/customer/modify/{orderno}")
	public String modifyCustomerPopup(@PathVariable final Long orderno, ModelMap model) {
		model.addAttribute("orderno", orderno);
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("smlist", codeService.findAllByUpCdId("SM", 2));
		model.addAttribute("sclist", codeService.findAllByUpCdId("SC", 2));
		return "order/popup/customer/order_modify";
	}

	//기성품
	@GetMapping("/popup/read-made/write")
	public String writeReadMadePopup(ModelMap model) {
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("smlist", codeService.findAllByUpCdId("SM", 2));
		model.addAttribute("sclist", codeService.findAllByUpCdId("SC", 2));
		model.addAttribute("today", Utils.getTodayDateFormat("yyyy-MM-dd"));
		return "order/popup/read-made/order_write";
	}

	@GetMapping("/popup/read-made/{orderno}")
	public String viewPopup(@PathVariable final Long orderno, ModelMap model) {
		model.addAttribute("orderno", orderno);
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[] {"ST","SM","SC"}, 2));
		return "order/popup/read-made/order_view";
	}
	
	@GetMapping("/popup/read-made/modify/{orderno}")
	public String modifyPopup(@PathVariable final Long orderno, ModelMap model) {
		model.addAttribute("orderno", orderno);
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("smlist", codeService.findAllByUpCdId("SM", 2));
		model.addAttribute("sclist", codeService.findAllByUpCdId("SC", 2));
		return "order/popup/read-made/order_modify";
	}

	@GetMapping("/popup/step/modify")
	public String stepPopup(@RequestParam(value = "ordersno") String ordersno, ModelMap model) {
		model.addAttribute("ordersno", ordersno);
		return "order/popup/order_step_modify";
	}

	@GetMapping("/schedule/list")
	public String scheduleList(ModelMap model) {
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("smlist", codeService.findAllByUpCdId("SM", 2));
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[]{"ST", "SM", "SC"}, 2));
		return "order/order_schedule_list";
	}
	
	@GetMapping("/stocked/list")
	public String stockedList(ModelMap model) {
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("smlist", codeService.findAllByUpCdId("SM", 2));
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[]{"ST", "SM", "SC"}, 2));
		return "order/order_stocked_list";
	}

	@GetMapping("/popup/customer/modify")
	public String customerModifyPopup(@RequestParam(value = "ordersno") String ordersno, ModelMap model) {
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[] {"ST","CT"}, 2));
		model.addAttribute("ordersno", ordersno);
		return "order/popup/order_customer_modify";
	}
	
	@GetMapping("/popup/vender/modify")
	public String venderModifyPopup(@RequestParam(value = "ordersno") String ordersno, ModelMap model) {
		model.addAttribute("cdmapper", codeService.findAllByUpCdId(new String[] {"MT","VT"}, 2));
		model.addAttribute("ordersno", ordersno);
		return "order/popup/order_vender_modify";
	}

	//주문관리-재고등록(고객)
	@GetMapping("/popup/orders/stock/write")
	public String stocksWritePopup(@RequestParam(value = "ordersno") String ordersno, ModelMap model) {
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("smlist", codeService.findAllByUpCdId("SM", 2));
		model.addAttribute("sclist", codeService.findAllByUpCdId("SC", 2));
		model.addAttribute("oclist", codeService.findAllByUpCdId("OC", 2, "ALL"));
		model.addAttribute("prevstocklist", stockService.findAllPrevStocks());
		model.addAttribute("ordersno", ordersno);
		return "order/popup/orders_stock_write";
	}
	
	//주문관리-재고등록(기성)
	@GetMapping("/popup/orders/nc-stock/write")
	public String ncStocksWritePopup(@RequestParam(value = "ordersno") String ordersno, ModelMap model) {
		model.addAttribute("stlist", codeService.findAllByUpCdId("ST", 2));
		model.addAttribute("smlist", codeService.findAllByUpCdId("SM", 2));
		model.addAttribute("sclist", codeService.findAllByUpCdId("SC", 2));
		model.addAttribute("oclist", codeService.findAllByUpCdId("OC", 2, "ALL"));
		model.addAttribute("prevstocklist", stockService.findAllPrevStocks());
		model.addAttribute("ordersno", ordersno);
		return "order/popup/orders_nc_stock_write";
	}
}
