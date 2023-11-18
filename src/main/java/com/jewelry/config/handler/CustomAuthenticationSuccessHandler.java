package com.jewelry.config.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.jewelry.authentication.jwt.entity.TokenVO;
import com.jewelry.config.provider.JwtTokenProvider;
import com.jewelry.response.ApiResponse;
import com.jewelry.response.ResponseCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        
    	// 전달받은 인증정보 SecurityContextHolder에 저장
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT Token 발급
//        final TokenVO token = jwtTokenProvider.generateToken(authentication);
        
        // Response
        ApiResponse.response(response, null, ResponseCode.AUTH_SUCCESS);
    }
    
}
