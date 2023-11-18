package com.jewelry.vender.entity;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.vender.dto.VenderDto;
import com.jewelry.vender.dto.VenderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VenderRepositoryCustom {

  Page<VenderResponseDto> getSearchVenders(final SearchDto searchDto, final Pageable pageable);

  VenderResponseDto getVenderByVenderNo(final Long venderNo);

  long updateVender(final VenderDto venderDto);

  long updateVendersToDelete(final VenderDto venderDto);
}
