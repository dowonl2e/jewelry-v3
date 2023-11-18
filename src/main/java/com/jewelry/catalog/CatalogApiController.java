package com.jewelry.catalog;

import com.jewelry.catalog.dto.CatalogDto;
import com.jewelry.catalog.dto.CatalogResponseDto;
import com.jewelry.catalog.dto.CatalogStoneDto;
import com.jewelry.catalog.model.CatalogService;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.config.provider.JwtTokenProvider;
import com.jewelry.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
public class CatalogApiController {

	private final CatalogService catalogService;

	private final JwtTokenProvider jwtTokenProvider;

	private final String menuId = "catalog";

	@GetMapping("/list")
	public Map<String, Object> list(
			@RequestHeader("Authorization") String accessToken,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId);
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		return catalogService.findAll(searchDto, pageable);
	}
	
	@PostMapping("/write")
	public ResponseEntity<Object> write(
			@RequestHeader("Authorization") String accessToken,
			@RequestPart(value = "file", required = false) MultipartFile file,
			final CatalogDto catalogDto, final CatalogStoneDto catalogStoneDto) throws Exception {
		catalogDto.setCatalogFile(file);
		catalogDto.setInptId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		String result = catalogService.saveCatalogAndStones(catalogDto, catalogStoneDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@GetMapping("/{catalogNo}")
	public CatalogResponseDto catalog(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long catalogNo,
			final CatalogDto catalogDto) throws Exception {
		catalogDto.setCatalogNo(catalogNo);
		catalogDto.setMenuId(menuId);
		catalogDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		return catalogService.findByCatalogNo(catalogDto);
	}

	@PatchMapping("/modify/{catalogNo}")
	public ResponseEntity<Object> modify(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final Long catalogNo,
			@RequestPart(value = "file", required = false) MultipartFile file,
			final CatalogDto catalogDto,
			final CatalogStoneDto catalogStoneDto) throws Exception {
		catalogDto.setCatalogFile(file);
		catalogDto.setCatalogNo(catalogNo);
		catalogDto.setUpdtId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		String result = catalogService.modifyCatalogAndStones(catalogDto, catalogStoneDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@PatchMapping("/catalogs/remove")
	public ResponseEntity<Object> catalogsRemove(
			@RequestHeader("Authorization") String accessToken,
			final CatalogDto catalogDto) throws Exception {
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		catalogDto.setMenuId(menuId);
		catalogDto.setUserId(userId);
		catalogDto.setUpdtId(userId);
		String result = catalogService.updateCatalogsToDelete(catalogDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}
}
