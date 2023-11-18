package com.jewelry.cms.menu;

import com.jewelry.authentication.jwt.values.Role;
import com.jewelry.cms.menu.domain.MenuAuthTO;
import com.jewelry.cms.menu.domain.MenuAuthVO;
import com.jewelry.cms.menu.dto.MenuAuthDto;
import com.jewelry.cms.menu.dto.MenuAuthResponseDto;
import com.jewelry.cms.menu.model.MenuAuthService;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.config.provider.JwtTokenProvider;
import com.jewelry.response.ResponseCode;
import com.jewelry.user.domain.UserTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menuauth")
public class MenuAuthApiController {

	private final MenuAuthService menuAuthService;

	private final JwtTokenProvider jwtTokenProvider;

	private final String menuId = "menu/auth";

	@GetMapping("/managers")
	public Map<String, Object> managers(
			@RequestHeader("Authorization") String accessToken,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId);
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		searchDto.setSearchUserRole(Role.MANAGER.getRole().substring(5));
		return menuAuthService.findAllUsers(searchDto, pageable);
	}

	@GetMapping("/menus/{userId}")
	public Map<String, Object> menusByUserId(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable("userId") final String tgt_user_id,
			final MenuAuthDto menuAuthDto){
		menuAuthDto.setMenuId(menuId);
		menuAuthDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		menuAuthDto.setTgtUserId(tgt_user_id);
		return menuAuthService.findAll(menuAuthDto);
	}

	@PostMapping("/menus")
	public ResponseEntity<Object> writeAuthMenus(
			@RequestHeader("Authorization") String accessToken,
			final MenuAuthDto menuAuthDto) {
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		menuAuthDto.setMenuId(menuId);
		menuAuthDto.setUserId(userId);
		menuAuthDto.setInptId(userId);
		String result = menuAuthService.bulkInsert(menuAuthDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@PostMapping("/menu")
	public ResponseEntity<Object> authMenu(
			@RequestHeader("Authorization") String accessToken,
			final MenuAuthDto menuAuthDto) {
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		menuAuthDto.setMenuId(menuId);
		menuAuthDto.setUserId(userId);
		menuAuthDto.setInptId(userId);
		String result = menuAuthService.save(menuAuthDto);

		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@PostMapping("/user/auth")
	public MenuAuthResponseDto userAuth(
			@RequestHeader("Authorization") String accessToken,
			@RequestBody final MenuAuthDto menuAuthDto){
		menuAuthDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		return menuAuthService.find(menuAuthDto);
	}
}
