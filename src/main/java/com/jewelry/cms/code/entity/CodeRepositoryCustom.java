package com.jewelry.cms.code.entity;

import com.jewelry.catalog.dto.CatalogResponseDto;
import com.jewelry.cms.code.dto.CodeDto;
import com.jewelry.cms.code.dto.CodeResponseDto;
import com.jewelry.common.domain.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CodeRepositoryCustom {

  Page<CodeResponseDto> getSearchCodes(final SearchDto searchDto, final Pageable pageable);

  Integer getMaxOrder(final String upCdId, final Integer cdDepth);

  CodeResponseDto getCode(final String cdId);

  List<CodeResponseDto> getLowCodes(final CodeDto codeDto);

  long updateCode(final CodeDto codeDto);

  long deleteCode(final String cdId);

  long deleteLowCodeByCdId(final String cdId);

  List<CodeResponseDto> getCodesByUpCdId(final CodeDto codeDto);

}
