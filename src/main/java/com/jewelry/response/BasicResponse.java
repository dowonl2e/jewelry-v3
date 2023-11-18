package com.jewelry.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicResponse<T> {
	
	private LocalDateTime timestamp = LocalDateTime.now();
	private int status;
	private String code;
	private String message;
	private int count;
	private T data;
	
	public BasicResponse(ResponseCode responseCode){
		this.status = responseCode.getStatus().value();
		this.code = responseCode.getCode();
		this.message = responseCode.getMessage();
	}
	
	public BasicResponse(ResponseCode responseCode, T data){
		this.status = responseCode.getStatus().value();
		this.code = responseCode.getCode();
		this.message = responseCode.getMessage();
		
		this.data = data;
		if(data instanceof List) {
			this.count = ((List<?>)data).size();
		}
		else {
			this.count = 1;
		}
	}
}