package com.jewelry.cash;

import com.jewelry.cash.dto.CashDto;
import com.jewelry.cash.dto.CashResponseDto;
import com.jewelry.cash.model.CashService;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.config.provider.JwtTokenProvider;
import com.jewelry.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cash")
@RequiredArgsConstructor
public class CashApiController {

	private final CashService cashService;

	private final JwtTokenProvider jwtTokenProvider;

	private final String menuId = "cash";

	@GetMapping("/list")
	public Map<String, Object> list(
			@RequestHeader("Authorization") String accessToken,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId);
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		return cashService.findAll(searchDto, pageable);
	}

	@PostMapping("/write")
	public ResponseEntity<Object> write(
			@RequestHeader("Authorization") String accessToken,
			final CashDto cashDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		cashDto.setMenuId(menuId);
		cashDto.setUserId(userId);
		cashDto.setInptId(userId);
		String result = cashService.save(cashDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@GetMapping("/{cashNo}")
	public CashResponseDto view(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long cashNo){
		CashDto cashDto = new CashDto();
		cashDto.setMenuId(menuId);
		cashDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		cashDto.setCashNo(cashNo);
		return cashService.findCash(cashDto);
	}
	

	@PatchMapping("/modify/{cashNo}")
	public ResponseEntity<Object> modify(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long cashNo,
			final CashDto cashDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		cashDto.setMenuId(menuId);
		cashDto.setUserId(userId);
		cashDto.setUpdtId(userId);
		cashDto.setCashNo(cashNo);
		cashDto.setInptId(userId);
		String result = cashService.update(cashDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@PatchMapping("/cash/remove/{cashNo}")
	public ResponseEntity<Object> cashRemove(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable("cashNo") Long cashNo){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		CashDto cashDto = new CashDto();
		cashDto.setCashNo(cashNo);
		cashDto.setMenuId(menuId);
		cashDto.setUserId(userId);
		cashDto.setUpdtId(userId);
		String result = cashService.updateCashToDelete(cashDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@PatchMapping("/cashes/remove")
	public ResponseEntity<Object> cashesRemove(
			@RequestHeader("Authorization") String accessToken,
			final CashDto cashDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		cashDto.setMenuId(menuId);
		cashDto.setUserId(userId);
		cashDto.setUpdtId(userId);
		String result = cashService.updateCashesToDelete(cashDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}
	
}
