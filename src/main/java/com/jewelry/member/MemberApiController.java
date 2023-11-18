package com.jewelry.member;

import com.jewelry.config.provider.JwtTokenProvider;
import com.jewelry.response.ResponseCode;
import com.jewelry.user.dto.UserDto;
import com.jewelry.user.dto.UserResponseDto;
import com.jewelry.user.model.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberApiController {

  private final UserService userService;

  private final JwtTokenProvider jwtTokenProvider;

  @GetMapping("/profile")
  public UserResponseDto findMemberProfile(@RequestHeader("Authorization") String accessToken) {
    return userService.find(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
  }

  @PostMapping("/profile/info")
  public UserResponseDto findMemberByToken(
      @RequestHeader("Authorization") String accessToken) {
    String resolveAccessToken = jwtTokenProvider.resolveToken(accessToken);
    if(ObjectUtils.isEmpty(resolveAccessToken)
        || !jwtTokenProvider.validateToken(resolveAccessToken)) {
      return null;
    }
    return userService.find(jwtTokenProvider.getPrincipal(resolveAccessToken));
  }

  @PatchMapping("/profile/modify")
  public ResponseEntity<Object> modify(
      @RequestHeader("Authorization") String accessToken,
      @RequestBody final UserDto userDto) {
    String userid = jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken));
    userDto.setUserId(userid);
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    userDto.setUserPwd(ObjectUtils.isEmpty(userDto.getUserPwd()) ? null : passwordEncoder.encode(userDto.getUserPwd()));
    userDto.setUpdtId(userid);
    String result = userService.update(userDto);

    ResponseCode response = result.equals("success") ? ResponseCode.SUCCESS : ResponseCode.INTERNAL_SERVER_ERROR;
    return new ResponseEntity<>(response.getStatus());
  }

}
