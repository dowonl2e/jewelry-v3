package com.jewelry.vender;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.config.provider.JwtTokenProvider;
import com.jewelry.response.ResponseCode;
import com.jewelry.vender.dto.VenderDto;
import com.jewelry.vender.dto.VenderPayDto;
import com.jewelry.vender.dto.VenderPayResponseDto;
import com.jewelry.vender.dto.VenderResponseDto;
import com.jewelry.vender.model.VenderPayService;
import com.jewelry.vender.model.VenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/vender")
@RequiredArgsConstructor
public class VenderApiController {
	
	private final VenderService venderService;

	private final VenderPayService venderPayService;
	private final JwtTokenProvider jwtTokenProvider;

	private final String menuId = "vender";

	@GetMapping("/list")
	public Map<String, Object> findAll(
			@RequestHeader("Authorization") String accessToken,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId);
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		return venderService.findAll(searchDto, pageable);
	}

	@PostMapping("/write")
	public ResponseEntity<Object> write(
			@RequestHeader("Authorization") String accessToken,
			@RequestBody final VenderDto searchDto) { //이쪽으로온 json 데이터 형식을 to에 넣어줌 requestbody
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		searchDto.setDelYn("N");
		searchDto.setMenuId(menuId);
		searchDto.setUserId(userId);
		searchDto.setInptId(userId);
		String result = venderService.save(searchDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@GetMapping("/{venderNo}")
	public VenderResponseDto findVenderByNo(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long venderNo) {
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		VenderDto venderDto = new VenderDto();
		venderDto.setMenuId(menuId);
		venderDto.setUserId(userId);
		venderDto.setVenderNo(venderNo);
		return venderService.findByVenderNo(venderDto);
	}

	@PatchMapping("/{venderNo}") // URL에 있는 {venderno} 을 아래의 넣기 위해 @PathVariable 을 쓴다.
	public ResponseEntity<Object> modify(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long venderNo,
			@RequestBody final VenderDto venderDto) {
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		venderDto.setMenuId(menuId);
		venderDto.setUserId(userId);
		venderDto.setVenderNo(venderNo);
		venderDto.setUpdtId(userId);
		String result = venderService.update(venderDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@PatchMapping("/venders/remove")
	public ResponseEntity<Object> venderRemove(
			@RequestHeader("Authorization") String accessToken,
			final VenderDto venderDto){ //파라미터로
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		venderDto.setMenuId(menuId);
		venderDto.setUserId(userId);
		venderDto.setUpdtId(userId);
		venderDto.setDelYn("Y");
		String result = venderService.updateVendersToDelete(venderDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR; //에러코드를 반환
		return new ResponseEntity<>(response.getStatus());
	}

	@GetMapping("/pay/list")
	public Map<String, Object> findAllVenderPay(
			@RequestHeader("Authorization") String accessToken,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId+"/pay");
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		return venderPayService.findAll(searchDto, pageable);
	}

	@PostMapping("/pay/write")
	public ResponseEntity<Object> payWrite(
			@RequestHeader("Authorization") String accessToken,
			@RequestBody final VenderPayDto venderPayDto) { //이쪽으로온 json 데이터 형식을 to에 넣어줌 requestbody
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		venderPayDto.setMenuId(menuId+"/pay");
		venderPayDto.setUserId(userId);
		venderPayDto.setInptId(userId);
		String result = venderPayService.save(venderPayDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@GetMapping("/pay/{payNo}")
	public VenderPayResponseDto findVenderPayByNo(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long payNo) {
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		VenderPayDto venderPayDto = new VenderPayDto();
		venderPayDto.setMenuId(menuId+"/pay");
		venderPayDto.setUserId(userId);
		venderPayDto.setPayNo(payNo);
		return venderPayService.findByPayNo(venderPayDto);
	}

	@PatchMapping("/pay/modify/{payNo}")
	public ResponseEntity<Object> payModify(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long payNo,
			@RequestBody final VenderPayDto venderPayDto) { //이쪽으로온 json 데이터 형식을 to에 넣어줌 requestbody
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		venderPayDto.setMenuId(menuId+"/pay");
		venderPayDto.setUserId(userId);
		venderPayDto.setPayNo(payNo);
		venderPayDto.setUpdtId(userId);
		String result = venderPayService.update(venderPayDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@PatchMapping("/pays/remove")
	public ResponseEntity<Object> paysRemove(
			@RequestHeader("Authorization") String accessToken,
			final VenderPayDto venderPayDto) { //이쪽으로온 json 데이터 형식을 to에 넣어줌 requestbody
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		venderPayDto.setMenuId(menuId+"/pay");
		venderPayDto.setUserId(userId);
		venderPayDto.setUpdtId(userId);
		String result = venderPayService.updateToDelete(venderPayDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}
}
