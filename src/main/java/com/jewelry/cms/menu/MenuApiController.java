package com.jewelry.cms.menu;

import com.jewelry.cms.menu.dto.MenuDto;
import com.jewelry.cms.menu.dto.MenuResponseDto;
import com.jewelry.cms.menu.model.MenuService;
import com.jewelry.config.provider.JwtTokenProvider;
import com.jewelry.response.BasicResponse;
import com.jewelry.response.ResponseCode;
import com.jewelry.values.JwtHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu")
public class MenuApiController {
	
	private final JwtTokenProvider jwtTokenProvider;
	
	private final MenuService menuService;

	@GetMapping("/user/menus")
	public ResponseEntity<BasicResponse<MenuResponseDto>> userMenus(HttpServletRequest request) {
		
		String bearerToken = request.getHeader(JwtHeader.AUTHORITY_TYPE_HEADER.getValue());
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtHeader.GRANT_TYPE_PREFIX.getValue()))
        	bearerToken = bearerToken.substring(7);

        if(bearerToken == null) 
        	return ResponseEntity.ok().body(new BasicResponse<>(ResponseCode.UNAUTHORIZED, null));
        
        String userId = jwtTokenProvider.getSubject(bearerToken);
        String userRole = jwtTokenProvider.getAuthorities(bearerToken);

		MenuDto menuDto = new MenuDto();
		MenuResponseDto menuResponseDto = new MenuResponseDto();
		menuDto.setUseYn("Y");
		menuDto.setMenuDepth(1);
		if(userRole.equals("ADMIN")) {
			menuResponseDto.setList(menuService.findAllByDepth(menuDto));
			menuDto.setMenuDepth(2);
			menuResponseDto.setSubList(menuService.findAllByDepth(menuDto));
		}
		else {
			menuDto.setUserId(userId);
			menuResponseDto.setList(menuService.findAllByUserId(menuDto));
			menuDto.setMenuDepth(2);
			menuResponseDto.setSubList(menuService.findAllByUserId(menuDto));
		}
		return ResponseEntity.ok().body(new BasicResponse<>(ResponseCode.AUTH_SUCCESS, menuResponseDto));
	}
}
