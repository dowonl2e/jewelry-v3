package com.jewelry.sale.entity;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.sale.dto.SaleDto;
import com.jewelry.sale.dto.SaleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SaleRepositoryCustom {

  Page<SaleResponseDto> getSearchSales(final SearchDto searchDto, final Pageable pageable);

  List<SaleResponseDto> getMonthlySalePriceStats(final SearchDto searchDto);

  long updateSalesToStock(final SaleDto saleDto);
}
