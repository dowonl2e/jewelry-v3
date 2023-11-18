package com.jewelry.customer;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.config.provider.JwtTokenProvider;
import com.jewelry.customer.dto.CustomerDto;
import com.jewelry.customer.dto.CustomerResponseDto;
import com.jewelry.customer.model.CustomerService;
import com.jewelry.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerApiController {
	
	private final CustomerService customerService;

	private final JwtTokenProvider jwtTokenProvider;

	private final String menuId = "customer";

	@GetMapping("/list")
	public Map<String, Object> findAll(
			@RequestHeader("Authorization") String accessToken,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId);
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		return customerService.findAll(searchDto, pageable);
	}

	@PostMapping("/write")
	public ResponseEntity<Object> write(
			@RequestHeader("Authorization") String accessToken,
			@RequestBody final CustomerDto customerDto) {
		customerDto.setInptId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		String result = customerService.save(customerDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return  new ResponseEntity<>(response.getStatus());
	}

	@GetMapping("/{customerNo}")
	public CustomerResponseDto findCodeByCdId(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long customerNo) {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setMenuId(menuId);
		customerDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		customerDto.setCustomerNo(customerNo);
		return customerService.findByCustomerNo(customerDto);
	}

	@PatchMapping("/{customerNo}")
	public ResponseEntity<Object> modify(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long customerNo,
			@RequestBody final CustomerDto customerDto) {
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		customerDto.setMenuId(menuId);
		customerDto.setUserId(userId);
		customerDto.setCustomerNo(customerNo);
		customerDto.setUpdtId(userId);
		String result = customerService.update(customerDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return  new ResponseEntity<>(response.getStatus());
	}

	@DeleteMapping("/{customerno}")
	public ResponseEntity<Object> remove(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long customerNo) {
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		CustomerDto customerDto = new CustomerDto();
		customerDto.setMenuId(menuId);
		customerDto.setUserId(userId);
		customerDto.setCustomerNo(customerNo);
		customerDto.setUpdtId(userId);
		String result = customerService.updateToDelete(customerDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return  new ResponseEntity<>(response.getStatus());
	}

	@PatchMapping("/customers/remove")
	public ResponseEntity<Object> customersRemove(
			@RequestHeader("Authorization") String accessToken,
			final CustomerDto customerDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		customerDto.setMenuId(menuId);
		customerDto.setUserId(userId);
		customerDto.setUpdtId(userId);
		String result = customerService.updateCustomersToDelete(customerDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}
	
}
