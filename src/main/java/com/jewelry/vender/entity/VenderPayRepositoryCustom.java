package com.jewelry.vender.entity;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.vender.dto.VenderPayDto;
import com.jewelry.vender.dto.VenderPayResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VenderPayRepositoryCustom {

  Page<VenderPayResponseDto> getSearchVenderPays(final SearchDto searchDto, final Pageable pageable);

  VenderPayResponseDto getVenderPayByPayNo(final Long payNo);

  long updateVenderPay(final VenderPayDto venderPayDto);

  long updateVenderPaysToDelete(final VenderPayDto venderPayDto);

}
