package com.jewelry.response;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;


import com.jewelry.authentication.jwt.entity.TokenVO;

import org.json.JSONObject;

public class ApiResponse {

	private static ApiResponse apiResponse = new ApiResponse();
	
	private ApiResponse() {}
	
	public static ApiResponse getInstance(){
        return apiResponse;
    }
	
	public static void response(HttpServletResponse response, ResponseCode code) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code.getStatus().value());

        JSONObject responseObj = ErrorResponse.toResponseJson(code);
        if(responseObj == null) {
        	response.sendError(code.getStatus().value());
        }
        response.getWriter().print(responseObj);
	}
	
	public static void response(HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
        response.setStatus(ResponseCode.SUCCESS.getStatus().value());

        JSONObject responseObj = ErrorResponse.toResponseJson(ResponseCode.SUCCESS);
        if(responseObj == null) {
        	response.sendError(ResponseCode.SUCCESS.getStatus().value());
        }
        response.getWriter().print(responseObj);
	}
	
	public static void response(HttpServletResponse response, TokenVO token, ResponseCode code) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code.getStatus().value());

        JSONObject responseObj = ErrorResponse.toResponseJson(code);
        if(responseObj == null) {
        	response.sendError(code.getStatus().value());
        }
        response.getWriter().print(responseObj);
	}
	
}
