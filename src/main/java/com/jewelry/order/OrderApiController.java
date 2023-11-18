package com.jewelry.order;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.config.provider.JwtTokenProvider;
import com.jewelry.order.dto.OrderDto;
import com.jewelry.order.dto.OrderResponseDto;
import com.jewelry.order.model.OrderService;
import com.jewelry.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderApiController {
	
	private final OrderService orderService;

	private final JwtTokenProvider jwtTokenProvider;

	private final String menuId = "order";

	@GetMapping("/list")
	public Map<String, Object> list(
			@RequestHeader("Authorization") String accessToken,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId);
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		return orderService.findAll(searchDto, pageable);
	}
	
	@PostMapping("/write")
	public ResponseEntity<Object> write(
			@RequestHeader("Authorization") String accessToken,
			@RequestPart(value = "file", required = false) MultipartFile file,
			final OrderDto orderDto) throws Exception {
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		orderDto.setMenuId(menuId);
		orderDto.setUserId(userId);
		orderDto.setOrderFile(file);
		orderDto.setInptId(userId);
		String result = orderService.save(orderDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@GetMapping("/{orderNo}")
	public OrderResponseDto order(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long orderNo){
		OrderDto orderDto = new OrderDto();
		orderDto.setMenuId(menuId);
		orderDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		orderDto.setOrderNo(orderNo);
		return orderService.findByOrderNo(orderDto);
	}

	@PatchMapping("/modify/{orderNo}")
	public ResponseEntity<Object> modify(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long orderNo,
			@RequestPart(value = "file", required = false) MultipartFile file,
			final OrderDto orderDto) throws Exception {
		String userid = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		orderDto.setMenuId(menuId);
		orderDto.setUserId(userid);
		orderDto.setOrderNo(orderNo);
		orderDto.setOrderFile(file);
		orderDto.setInptId(userid);
		orderDto.setUpdtId(userid);
		String result = orderService.update(orderDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@PatchMapping("/step/modify")
	public ResponseEntity<Object> stepModify(
			@RequestHeader("Authorization") String accessToken,
			final OrderDto orderDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		orderDto.setUserId(userId);
		orderDto.setUpdtId(userId);
		String result = orderService.updateOrdersStep(orderDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@PatchMapping("/orders/remove")
	public ResponseEntity<Object> ordersRemove(
			@RequestHeader("Authorization") String accessToken,
			final OrderDto orderDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		orderDto.setMenuId(menuId);
		orderDto.setUserId(userId);
		orderDto.setUpdtId(userId);
		String result = orderService.updateOrdersToDelete(orderDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@GetMapping("/schedule/list")
	public Map<String, Object> scheduleList(
			@RequestHeader("Authorization") String accessToken,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId+"/schedule");
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		searchDto.setSearchStep("A");
		return orderService.findAll(searchDto, pageable);
	}

	@GetMapping("/stocked/list")
	public Map<String, Object> stockedList(
			@RequestHeader("Authorization") String accessToken,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId+"/stocked");
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		searchDto.setSearchStep("B");
		return orderService.findAll(searchDto, pageable);
	}

	@PatchMapping("/customer/modify")
	public ResponseEntity<Object> customerModify(
			@RequestHeader("Authorization") String accessToken,
			final OrderDto orderDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		orderDto.setUserId(userId);
		orderDto.setUpdtId(userId);
		String result = orderService.updateOrdersCustomer(orderDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@PatchMapping("/vender/modify")
	public ResponseEntity<Object> venderModify(
			@RequestHeader("Authorization") String accessToken,
			final OrderDto orderDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		orderDto.setUserId(userId);
		orderDto.setUpdtId(userId);
		String result = orderService.updateOrdersVender(orderDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@PostMapping("/list/nos")
	public Map<String, Object> listByOrdersNo(
			@RequestHeader("Authorization") String accessToken,
			final OrderDto orderDto){
		orderDto.setMenuId(menuId);
		orderDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		return orderService.findAllByOrderNos(orderDto);
	}

	@GetMapping("/customer/list/{customerNo}")
	public Map<String, Object> orderCustomerList(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable("customerNo") final Long customerNo,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId);
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		searchDto.setCustomerNo(customerNo);
		return orderService.findAllCustomerOrder(searchDto, pageable);
	}

	@GetMapping("/customer/list/{customerNo}/{orderStep}")
	public Map<String, Object> orderCustomerList(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable("customerNo") final Long customerNo,
			@PathVariable("orderStep") final String orderStep,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId);
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		searchDto.setOrderStep(orderStep);
		searchDto.setCustomerNo(customerNo);
		return orderService.findAllCustomerOrder(searchDto, pageable);
	}

//	@PatchMapping("/orders/stock/write")
//	public ResponseEntity<Object> ordersStockWrite(
//			@RequestHeader("Authorization") String accessToken,
//			@RequestPart(value = "file", required = false) MultipartFile file,
//			final StockTO to, final OrderDto orderDto){
//		String userid = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
//		to.setInpt_id(userid);
//		to.setUpdt_id(userid);
//		to.setStockfile(file);
//		orderDto.setUpdtId(userid);
//		String result = orderService.insertOrdersToStock(to, orderto);
//
//		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
//		return new ResponseEntity<>(response.getStatus());
//	}

}
