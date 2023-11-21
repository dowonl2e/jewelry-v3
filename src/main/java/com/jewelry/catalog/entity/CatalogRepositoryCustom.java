package com.jewelry.catalog.entity;

import com.jewelry.catalog.dto.CatalogDto;
import com.jewelry.catalog.dto.CatalogResponseDto;
import com.jewelry.catalog.dto.CatalogStoneDto;
import com.jewelry.catalog.dto.CatalogStoneResponseDto;
import com.jewelry.common.domain.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CatalogRepositoryCustom {

  Page<CatalogResponseDto> getSearchCatalogs(final SearchDto searchDto, final Pageable pageable);

  CatalogResponseDto getCatalog(final Long catalogNo);

  List<CatalogStoneResponseDto> getCatalogStones(final Long catalogNo);

  Long updateCatalog(final CatalogDto catalogDto);

  Long deleteCatalogStonesByCatalogNo(final Long catalogNo);

  Long updateCatalogsToDelete(final CatalogDto catalogDto);

}
