package com.jewelry.repair;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.config.provider.JwtTokenProvider;
import com.jewelry.repair.dto.RepairDto;
import com.jewelry.repair.dto.RepairResponseDto;
import com.jewelry.repair.model.RepairService;
import com.jewelry.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/repair")
@RequiredArgsConstructor
public class RepairApiController {
	
	private final RepairService repairService;

	private final JwtTokenProvider jwtTokenProvider;

	private final String menuId = "repair";

	@GetMapping("/list")
	public Map<String, Object> list(
			@RequestHeader("Authorization") String accessToken,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId);
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		return repairService.findAll(searchDto, pageable);
	}
	
	@PostMapping("/write")
	public ResponseEntity<Object> write(
			@RequestHeader("Authorization") String accessToken,
			@RequestPart(value = "file", required = false) MultipartFile file,
			final RepairDto repairDto) {
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		repairDto.setMenuId(menuId);
		repairDto.setUserId(userId);
		repairDto.setRepairFile(file);
		repairDto.setInptId(userId);
		String result = repairService.save(repairDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}
	
	@GetMapping("/{repairNo}")
	public RepairResponseDto repair(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long repairNo){
		RepairDto repairDto = new RepairDto();
		repairDto.setMenuId(menuId);
		repairDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		repairDto.setRepairNo(repairNo);
		return repairService.findByRepairNo(repairDto);
	}
	
	@PatchMapping("/modify/{repairNo}")
	public ResponseEntity<Object> modify(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long repairNo,
			@RequestPart(value = "file", required = false) MultipartFile file,
			final RepairDto repairDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		repairDto.setMenuId(menuId);
		repairDto.setUserId(userId);
		repairDto.setRepairFile(file);
		repairDto.setRepairNo(repairNo);
		repairDto.setInptId(userId);
		repairDto.setUpdtId(userId);
		String result = repairService.update(repairDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@PatchMapping("/repairs/remove")
	public ResponseEntity<Object> repairsRemove(
			@RequestHeader("Authorization") String accessToken,
			final RepairDto repairDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		repairDto.setMenuId(menuId);
		repairDto.setUserId(userId);
		repairDto.setUpdtId(userId);
		String result = repairService.updateRepairsToDelete(repairDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}
	
	@PatchMapping("/repairs/complete")
	public ResponseEntity<Object> repairsComplete(
			@RequestHeader("Authorization") String accessToken,
			final RepairDto repairDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		repairDto.setMenuId(menuId);
		repairDto.setUserId(userId);
		repairDto.setUpdtId(userId);
		String result = repairService.updateRepairsToComplete(repairDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}
	
	@GetMapping("/customer/list/{customerNo}")
	public Map<String, Object> customerList(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long customerNo,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		searchDto.setMenuId(menuId);
		searchDto.setUserId(userId);
		searchDto.setCustomerNo(customerNo);
		return repairService.findAllCustomerRepairs(searchDto, pageable);
	}
}
