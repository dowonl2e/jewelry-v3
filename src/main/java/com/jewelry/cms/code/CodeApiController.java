package com.jewelry.cms.code;

import com.jewelry.cms.code.dto.CodeDto;
import com.jewelry.cms.code.dto.CodeResponseDto;
import com.jewelry.cms.code.model.CodeService;
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
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class CodeApiController {

	private final CodeService codeService;

	private final JwtTokenProvider jwtTokenProvider;

	private final String menuId = "code";

	@GetMapping("/list")
	public Map<String, Object> findAll(
			@RequestHeader("Authorization") String accessToken,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId);
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		searchDto.setCdDepth(1);
		return codeService.findAll(searchDto, pageable);
	}
	
	@PostMapping("/write")
	public ResponseEntity<Object> write(
			@RequestHeader("Authorization") String accessToken,
			@RequestBody final CodeDto codeDto) {
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		codeDto.setMenuId(menuId);
		codeDto.setUserId(userId);
		codeDto.setInptId(userId);
		String result = codeService.save(codeDto);
		
		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return  new ResponseEntity<>(response.getStatus());

	}

	@GetMapping("/{cdId}")
	public CodeResponseDto findCodeByCdId(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final String cdId) {
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		CodeDto codeDto = new CodeDto();
		codeDto.setMenuId(menuId);
		codeDto.setUserId(userId);
		codeDto.setCdId(cdId);
		return codeService.findByCdId(codeDto);
	}
	
	@PatchMapping("/{cdId}")
	public ResponseEntity<Object> modify(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final String cdId,
			@RequestBody final CodeDto codeDto){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		codeDto.setMenuId(menuId);
		codeDto.setUserId(userId);
		codeDto.setUpdtId(userId);
		codeDto.setCdId(cdId);
		String result = codeService.update(codeDto);
		
		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@DeleteMapping("/{cdId}")
	public String remove(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final String cdId) {
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		CodeDto codeDto = new CodeDto();
		codeDto.setMenuId(menuId);
		codeDto.setUserId(userId);
		codeDto.setCdId(cdId);
		return codeService.remove(codeDto);
	}
	
	//********************************하위코드********************************
	@GetMapping("/list/{upCdId}/{cdDepth}")
	public Map<String, Object> findAll(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable("upCdId") final String upCdId,
			@PathVariable("cdDepth") final Integer cdDepth){
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		CodeDto codeDto = new CodeDto();
		codeDto.setMenuId(menuId);
		codeDto.setUserId(userId);
		codeDto.setUpCdId(upCdId);
		codeDto.setCdDepth(cdDepth);
		return codeService.findAllSubCode(codeDto);
	}
}
