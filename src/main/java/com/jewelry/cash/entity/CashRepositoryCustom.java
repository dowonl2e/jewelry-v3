package com.jewelry.cash.entity;

import com.jewelry.cash.dto.CashDto;
import com.jewelry.cash.dto.CashResponseDto;
import com.jewelry.common.domain.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CashRepositoryCustom {

  Page<CashResponseDto> getSearchCashes(final SearchDto searchDto, final Pageable pageable);

  List<CashResponseDto> getCashStatsList(final SearchDto searchDto, final Pageable pageable);

  List<CashResponseDto> getCashMaterialStatsList(final SearchDto searchDto, final Pageable pageable);

  List<CashResponseDto> getTodayMaterialCashStatsList(final SearchDto searchDto, final Pageable pageable);

  CashResponseDto getCash(Long cashNo);

  long updateCash(final CashDto cashDto);

  long updateCashToDelete(final CashDto cashDto);

  long updateCashesToDelete(final CashDto cashDto);

}
