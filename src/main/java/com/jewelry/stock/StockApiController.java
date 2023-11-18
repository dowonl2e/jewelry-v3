package com.jewelry.stock;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.config.provider.JwtTokenProvider;
import com.jewelry.response.ResponseCode;
import com.jewelry.stock.dto.StockDto;
import com.jewelry.stock.dto.StockResponseDto;
import com.jewelry.stock.model.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockApiController {
	
	private final StockService stockService;

	private final JwtTokenProvider jwtTokenProvider;

	private final String menuId = "stock";
	@GetMapping("/list")
	public Map<String, Object> list(
			@RequestHeader("Authorization") String accessToken,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId);
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		return stockService.findAll(searchDto, pageable);
	}
	
	@PostMapping("/write")
	public ResponseEntity<Object> write(
			@RequestHeader("Authorization") String accessToken,
			@RequestPart(value = "file", required = false) MultipartFile file,
			final StockDto stockDto) throws Exception {
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		stockDto.setMenuId(menuId);
		stockDto.setUserId(userId);
		stockDto.setStockFile(file);
		stockDto.setInptId(userId);
		String result = stockService.save(stockDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@GetMapping("/{stockNo}")
	public StockResponseDto stock(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long stockNo){
		StockDto stockDto = new StockDto();
		stockDto.setMenuId(menuId);
		stockDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		stockDto.setStockNo(stockNo);
		return stockService.findByStockNo(stockDto);
	}

	@GetMapping("/customer/{stockno}")
	public StockResponseDto stockCustomer(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long stockNo){
		StockDto stockDto = new StockDto();
		stockDto.setMenuId(menuId);
		stockDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		stockDto.setStockNo(stockNo);
		return stockService.findCustomerByStockNo(stockDto);
	}

	@PatchMapping("/modify/{stockNo}")
	public ResponseEntity<Object> modify(
			@RequestHeader("Authorization") String accessToken,
			@RequestPart(value = "file", required = false) MultipartFile file,
			@PathVariable final Long stockNo,
			final StockDto stockDto) throws Exception {
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		stockDto.setMenuId(menuId);
		stockDto.setUserId(userId);
		stockDto.setStockNo(stockNo);
		stockDto.setStockFile(file);
		stockDto.setInptId(userId);
		stockDto.setUpdtId(userId);
		String result = stockService.update(stockDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}
	
	@PatchMapping("/stocks/remove")
	public ResponseEntity<Object> ordersRemove(
			@RequestHeader("Authorization") String accessToken,
			final StockDto stockDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		stockDto.setMenuId(stockDto.getMenuId() == null ? menuId : stockDto.getMenuId());
		stockDto.setUserId(userId);
		stockDto.setUpdtId(userId);
		String result = stockService.updateStocksToDelete(stockDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@GetMapping("/stocks/valid/customer")
	public Map<String, Object> checkCustomer(
			@RequestHeader("Authorization") String accessToken,
			@RequestParam("stocksNo") final String stocksNo){
		return stockService.isSameCustomer(stocksNo);
	}

	@PatchMapping("/stocks/sale")
	public ResponseEntity<Object> ordersSale(
			@RequestHeader("Authorization") String accessToken,
			final StockDto stockDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		stockDto.setMenuId(menuId);
		stockDto.setUserId(userId);
		stockDto.setUpdtId(userId);
		String result = stockService.updateStocksToSale(stockDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}
	
	@GetMapping("/accumulation/list")
	public Map<String, Object> accumulations(
			@RequestHeader("Authorization") String accessToken,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId+"/accumulation");
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		return stockService.findAllAccumulationStock(searchDto, pageable);
	}

	@PatchMapping("/reg-date/modify")
	public ResponseEntity<Object> regDateModify(
			@RequestHeader("Authorization") String accessToken,
			final StockDto stockDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		stockDto.setMenuId(menuId);
		stockDto.setUserId(userId);
		stockDto.setUpdtId(userId);
		String result = stockService.updateStocksRegDt(stockDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@PatchMapping("/type/modify")
	public ResponseEntity<Object> typeModify(
			@RequestHeader("Authorization") String accessToken,
			final StockDto stockDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		stockDto.setMenuId(menuId);
		stockDto.setUserId(userId);
		stockDto.setUpdtId(userId);
		String result = stockService.updateStocksType(stockDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@PostMapping("/order/customer/write")
	public ResponseEntity<Object> orderCustomerWrite(
			@RequestHeader("Authorization") String accessToken,
			final StockDto stockDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		stockDto.setMenuId(menuId);
		stockDto.setUserId(userId);
		stockDto.setInptId(userId);
		stockDto.setUpdtId(userId);
		String result = stockService.saveCustomerOrder(stockDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@PatchMapping("/vender/modify")
	public ResponseEntity<Object> venderModify(
			@RequestHeader("Authorization") String accessToken,
			final StockDto stockDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		stockDto.setMenuId(menuId);
		stockDto.setUserId(userId);
		stockDto.setUpdtId(userId);
		String result = stockService.updateStocksVender(stockDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}
}
