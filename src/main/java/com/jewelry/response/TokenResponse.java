package com.jewelry.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenResponse {
    private final String grantType;
    private final String accessToken;

    private final Long accessTokenExpioresIn;
}
