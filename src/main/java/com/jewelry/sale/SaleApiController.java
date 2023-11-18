package com.jewelry.sale;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.config.provider.JwtTokenProvider;
import com.jewelry.response.ResponseCode;
import com.jewelry.sale.dto.SaleDto;
import com.jewelry.sale.model.SaleService;
import com.jewelry.stock.dto.StockDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SaleApiController {

	private final SaleService saleService;

	private final JwtTokenProvider jwtTokenProvider;

	private final String menuId = "sale";

	@GetMapping("/list")
	public Map<String, Object> list(
			@RequestHeader("Authorization") String accessToken,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId);
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		return saleService.findAll(searchDto, pageable);
	}
	
	@PatchMapping("/sales/stock/modify")
	public ResponseEntity<Object> salesStockModify(
			@RequestHeader("Authorization") String accessToken,
			final SaleDto saleDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		saleDto.setMenuId(menuId);
		saleDto.setUserId(userId);
		saleDto.setUpdtId(userId);
		String result = saleService.deleteSalesToStock(saleDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}
	
	@PatchMapping("/sales/customer/modify")
	public ResponseEntity<Object> salesCustomerModify(
			@RequestHeader("Authorization") String accessToken,
			final SaleDto saleDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		saleDto.setMenuId(menuId);
		saleDto.setUserId(userId);
		saleDto.setUpdtId(userId);
		String result = saleService.updateSalesCustomer(saleDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}
	
	@PatchMapping("/sales/date/modify")
	public ResponseEntity<Object> salesDateModify(
			@RequestHeader("Authorization") String accessToken,
			final SaleDto saleDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		saleDto.setMenuId(menuId);
		saleDto.setUserId(userId);
		saleDto.setUpdtId(userId);
		String result = saleService.updateStocksSaleDate(saleDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}
	
}
