package com.jewelry.config.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.jewelry.response.ResponseCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
    	
    	authException.printStackTrace();
    	
		log.error("CustomAuthenticationEntryPoint : " + ResponseCode.UNAUTHORIZED);
		
		throw new AccessDeniedException(ResponseCode.UNAUTHORIZED.getMessage());
	        
    }

}
