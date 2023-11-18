package com.jewelry.authentication.jwt.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@RedisHash("refreshToken")
@NoArgsConstructor
@Entity(name = "TB_REFRESH_TOKEN")
public class RefreshToken  {
	
	@Id
	@Column(name = "KEY_ID")
	private String keyId;
	@Column(name = "TOKEN_VALUE")
	private String tokenValue;
	
    //@TimeToLive
    @Column(name = "EXPIRED_DT")
	private LocalDateTime expiredDt;
	@Column(name = "INPT_DT")
	private LocalDateTime inptDt = LocalDateTime.now();
	@Column(name = "UPDT_DT")
	private LocalDateTime updtDt;
	
	@Builder
	public RefreshToken(String keyId, String tokenValue, LocalDateTime expiredDt) {
		this.keyId = keyId;
		this.tokenValue = tokenValue;
		this.expiredDt = expiredDt;
	}

	public void update(String tokenValue, LocalDateTime expiredDate) {
		this.tokenValue = tokenValue;
		this.expiredDt = expiredDate;
		this.updtDt = LocalDateTime.now();
	}
	
}
