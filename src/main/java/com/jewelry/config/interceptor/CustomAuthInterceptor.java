package com.jewelry.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jewelry.cms.menu.service.MenuAuthService;
import com.jewelry.config.provider.AuthorizationExtractor;
import com.jewelry.config.provider.JwtTokenProvider;
import com.jewelry.exception.CustomException;
import com.jewelry.response.ResponseCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthInterceptor implements HandlerInterceptor {
	
	private static final String AUTH_TYPE_PREFIX = "Bearer";
	
	private final MenuAuthService authService;
	private final AuthorizationExtractor authorizationExtractor;
	private final JwtTokenProvider jwtTokenProvider;
	
	/**
	 * 컨트롤러의 메서드에 매핑된 특정 URI를 호출했을 때 컨트롤러를 접근하기 전 실행되는 메서드
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String url = request.getRequestURI();
		if(!url.contains("image/display")) {
			log.info("=================== AUTH BEGIN ====================");
			log.info("Request URI(PreHandle) ==> " + url);
			log.info("===================================================");
			
			String token = authorizationExtractor.extract(request, AUTH_TYPE_PREFIX);
			log.debug("CustomAuthInterceptor(preHandle) : " + token);
			if(ObjectUtils.isEmpty(token)) {
				throw new CustomException(ResponseCode.INVALID_AUTH_TOKEN);
			}

			if(!jwtTokenProvider.validateToken(token)) {
				throw new CustomException(ResponseCode.EXPIRED_AUTH_TOKEN);
			}
			
			request.setAttribute("name", jwtTokenProvider.getSubject(token));
		}

		return true;

	}
	
}
