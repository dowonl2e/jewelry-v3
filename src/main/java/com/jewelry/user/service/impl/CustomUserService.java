package com.jewelry.user.service.impl;

import com.jewelry.authentication.jwt.entity.RefreshToken;
import com.jewelry.authentication.jwt.entity.RefreshTokenRepository;
import com.jewelry.authentication.jwt.entity.TokenVO;
import com.jewelry.config.provider.JwtTokenProvider;
import com.jewelry.config.redis.service.RedisService;
import com.jewelry.response.ResponseCode;
import com.jewelry.user.entity.CustomUserDetails;
import com.jewelry.user.entity.UserEntity;
import com.jewelry.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserService {

	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisService redisService;

	@Transactional
	public TokenVO login(String id, String password) {

		UserDetails userDetails = userRepository.findByUserId(id)
							.map(this::createUserDetails)
							.orElseThrow(() -> new UsernameNotFoundException(ResponseCode.USER_INVALID.getMessage()));

		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new UsernameNotFoundException(ResponseCode.USER_INVALID.getMessage());
		}

		TokenVO tokenVo = jwtTokenProvider.generateToken(userDetails);
		Optional<RefreshToken> refreshTokenEntity = refreshTokenRepository.findByKeyId(userDetails.getUsername());

		//refreshToken 정보 여부 및 만료여부 체크
//		if(refreshTokenEntity.isEmpty()) {
//			RefreshToken refreshToken = RefreshToken.builder()
//					.keyId(userDetails.getUsername())
//					.tokenValue(tokenVo.getRefreshToken())
//					.expiredDt(tokenVo.getRefreshTokeExpioresIn())
//					.build();
//
//			refreshTokenRepository.save(refreshToken);
//		}
//		else {
//			//Refresh Token 만료 체크
//			if(jwtTokenProvider.validateToken(refreshTokenEntity.get().getTokenValue()) == false)
//			refreshTokenEntity.get().update(tokenVo.getRefreshToken(), tokenVo.getRefreshExpiredDate());
//		}

		// 3. 인증 정보를 기반으로 JWT 토큰 생성한 토큰 Context에 저장
		Authentication authentication = jwtTokenProvider.getAuthentication(tokenVo.getAccessToken());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return tokenVo;
	}

	// 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
	private UserDetails createUserDetails(UserEntity user) {
    	return new CustomUserDetails(user);
    }

}
