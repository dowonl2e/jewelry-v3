package com.jewelry.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("===============================================");
		log.debug("==================== BEGIN ====================");
		log.debug("Request URI ===> " + request.getRequestURI());
		log.debug("===============================================");
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.debug("===============================================");
		log.debug("===================== END =====================");
		log.debug("Request URI ===> " + request.getRequestURI());
		log.debug("===============================================");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	
}