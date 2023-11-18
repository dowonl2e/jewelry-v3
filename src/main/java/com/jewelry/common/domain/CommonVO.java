package com.jewelry.common.domain;

import com.jewelry.cms.menu.domain.MenuAuthVO;
import com.jewelry.cms.menu.dto.MenuAuthResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommonVO {
	
	private String userId;
	private String inptId;
	private String inptNm;

	protected LocalDateTime inptDt;
	private String updtId;
	private LocalDateTime updtDt;

	private MenuAuthResponseDto menuAuthResponseDto;
	private MenuAuthVO menuAuthVO;

}
