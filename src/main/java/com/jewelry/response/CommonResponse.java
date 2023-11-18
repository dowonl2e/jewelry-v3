package com.jewelry.response;

import java.util.ArrayList;

import lombok.Builder;
import lombok.ToString;

@ToString
public class CommonResponse {
	
	private final int status;
    private final String message;
    private final Integer size;
    private final ArrayList<?> items;

    @Builder
    public CommonResponse(int status, String message, Integer size, ArrayList<?> items) {
        this.status = status;
        this.message = message;
        this.size = size;
        this.items = items;
    }
}
