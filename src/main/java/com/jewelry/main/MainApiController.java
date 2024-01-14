package com.jewelry.main;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.elasticsearch.model.IntegrationSearchService;
import com.jewelry.main.model.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainApiController {

	private final MainService mainService;
	private final IntegrationSearchService integrationSearchService;

	@GetMapping("/stats/all")
	public Map<String, Object> statsAll(final SearchDto searchDto){
		return mainService.findAllStats(searchDto);
	}
	
	@GetMapping("/monthly/sale/price")
	public Map<String, Object> salePriceStats(final SearchDto searchDto){
		return mainService.findMonthlySalePrice(searchDto);
	}

	@GetMapping("/material/orders")
	public Map<String, Object> materialOrdersStats(final SearchDto searchDto){
		return mainService.findMaterialOrders(searchDto);
	}

	@GetMapping("/material/stocks")
	public Map<String, Object> materialStocksStats(final SearchDto searchDto){
		return mainService.findMaterialStocks(searchDto);
	}

	@GetMapping("/integration/search")
	public Map<String, Object> integrationSearchAll(@RequestParam("keyword") String keyword){
		Pageable pageable = PageRequest.of(0, 10);
		return integrationSearchService.findAll(keyword, pageable);
	}
}
