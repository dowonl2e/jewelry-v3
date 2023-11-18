package com.jewelry.values;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtHeader {

	AUTHORITY_TYPE_HEADER("Authorization", "JWT 타입 / Authorization"),
	GRANT_TYPE_PREFIX("Bearer ", "JWT 타입 / Bearer ");

  private String value;
  private String description;
    
}
