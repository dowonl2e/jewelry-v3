package com.jewelry.response;

import lombok.Builder;
import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

	private final LocalDateTime timestamp = LocalDateTime.now();
	private final int status;
	private final String code;
	private final String message;
	
	public ErrorResponse(int status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
	
	public static ResponseEntity<ErrorResponse> toResponseEntity(ResponseCode errorCode){
		return ResponseEntity
				.status(errorCode.getStatus())
				.body(ErrorResponse.builder()
						.status(errorCode.getStatus().value())
						.code(errorCode.getCode())
						.message(errorCode.getMessage())
						.build());
	}
	
	public static JSONObject toResponseJson(ResponseCode code) {
        try {
    		
        	JSONObject jsonObj = new JSONObject();
    			jsonObj.put("status", code.getStatus().value());
        	jsonObj.put("code", code.getCode());
	        jsonObj.put("message", code.getMessage());
	        return jsonObj;
	        
        } catch(JSONException e) {
        	return null;
        }
	}
	
}
