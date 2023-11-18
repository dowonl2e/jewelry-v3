package com.jewelry.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

public class ResponseHandler {
	
	public static ResponseEntity<Object> getResponse(String message, HttpStatus status, Object responseObj){
		message = ObjectUtils.isEmpty(message) ? ResponseCode.INTERNAL_SERVER_ERROR.getMessage() : message;
		status = ObjectUtils.isEmpty(status) ? ResponseCode.INTERNAL_SERVER_ERROR.getStatus() : status;
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("message", message);
		responseMap.put("status", status.value());
		responseMap.put("data", responseObj);
        
		return new ResponseEntity<Object>(responseMap, status);
	}
}
