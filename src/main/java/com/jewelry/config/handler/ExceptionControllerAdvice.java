package com.jewelry.config.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jewelry.exception.CustomException;
import com.jewelry.response.ErrorResponse;
import com.jewelry.response.ResponseCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

	/*
     * Developer Custom Exception
     */
	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<ErrorResponse> handleCustomException(final CustomException e){
        log.error("handleCustomException: {}", e);
        return ErrorResponse.toResponseEntity(e.getErrorCode());
	}
	
	/*
     * UsernameNotFoundException Custom Exception
     */
	@ExceptionHandler(UsernameNotFoundException.class)
	protected ResponseEntity<ErrorResponse> usernameNotFoundException(final UsernameNotFoundException e){
        log.error("usernameNotFoundException: {}", e);
        return ErrorResponse.toResponseEntity(ResponseCode.USER_INVALID);
	}
	
	/*
	 * HTTP 401 Exception
	 */
	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<ErrorResponse> accessDeniedException(final AccessDeniedException e){
		log.error("accessDeniedException {}", e);
        return ErrorResponse.toResponseEntity(ResponseCode.UNAUTHORIZED);
	}
	
	/*
	 * HTTP 405 Exception
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e){
		log.error("handleHttpRequestMethodNotSupportedException {}", e);
        return ErrorResponse.toResponseEntity(ResponseCode.METHOD_NOT_ALLOWED);
	}
	
	 /*
     * HTTP 500 Exception
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
        log.info("handleException: {}", e.getClass().getName());
        log.error("handleException: {}", e);
        return ErrorResponse.toResponseEntity(ResponseCode.INTERNAL_SERVER_ERROR);
    }
}
