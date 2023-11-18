package com.jewelry.user;

import com.jewelry.authentication.jwt.values.Role;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.config.provider.JwtTokenProvider;
import com.jewelry.response.ResponseCode;
import com.jewelry.user.domain.UserTO;
import com.jewelry.user.domain.UserVO;
import com.jewelry.user.dto.UserDto;
import com.jewelry.user.dto.UserResponseDto;
import com.jewelry.user.model.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {
	
	private final UserService userService;

	private final JwtTokenProvider jwtTokenProvider;
	private final String menuId = "user";

	@GetMapping("/list")
	public Map<String, Object> findAllUser(
			@RequestHeader("Authorization") String accessToken,
			final SearchDto searchDto){
		Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
		searchDto.setMenuId(menuId);
		searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
		return userService.findAll(searchDto, pageable);
	}
	
	@PostMapping("/write")
	public ResponseEntity<Object> write(
			@RequestHeader("Authorization") String accessToken,
			@RequestBody final UserDto userDto) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		userDto.setMenuId(menuId);
		userDto.setTgtUserId(userDto.getUserId());
		userDto.setUserId(userId);
		userDto.setInptId(userId);
		userDto.setUserPwd(passwordEncoder.encode(userDto.getUserPwd()));
		userDto.setUserRole(Role.MANAGER);
		String result = userService.save(userDto);
		
		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}

	@GetMapping("/{tgtUserId}")
	public UserResponseDto findUser(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable final String tgtUserId) {
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		UserDto userDto = new UserDto();
		userDto.setMenuId(menuId);
		userDto.setUserId(userId);
		userDto.setTgtUserId(tgtUserId);
		return userService.find(userDto);
	}

	@PatchMapping("/modify/{tgtUserId}")
	public ResponseEntity<Object> modify(
			@RequestHeader("Authorization") String accessToken,
			@PathVariable("tgtUserId") final String tgtUserId,
			@RequestBody final UserDto userDto) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String userId = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
		userDto.setMenuId(menuId);
		userDto.setUserId(userId);
		userDto.setTgtUserId(tgtUserId);
		userDto.setUserPwd(ObjectUtils.isEmpty(userDto.getUserPwd()) ? null : passwordEncoder.encode(userDto.getUserPwd()));
		userDto.setUpdtId(userId);
		String result = userService.update(userDto);
		
		ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(response.getStatus());
	}
}
