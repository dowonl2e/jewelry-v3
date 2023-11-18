package com.jewelry.authentication.jwt.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TokenTO {

	private String userId;
	private String accessToken;
	private String refreshToken;
	
	
	
}
