package com.jewelry.stock.entity;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.stock.dto.StockDto;
import com.jewelry.stock.dto.StockResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StockRepositoryCustom {

  Page<StockResponseDto> getSearchStocks(final SearchDto searchDto, final Pageable pageable);

  List<StockResponseDto> getPrevStocks();

  StockResponseDto getStock(final Long stockNo);
  StockResponseDto getStockCustomer(final Long stockNo);
  long updateStockToDelete(final StockDto stockDto);

  long updateStock(final StockDto stockDto);

  long updateStocksToSale(final StockDto stockDto);

  long updateStocksRegDt(final StockDto stockDto);

  long updateStocksType(final StockDto stockDto);
  long updateStocksVender(final StockDto stockDto);
  long updateStocksToDelete(final StockDto stockDto);
  long updateStocksOrder(final StockDto stockDto);

  long updateStocksCustomer(final StockDto stockDto);
  long updateStocksSaleDate(final StockDto stockDto);

  Page<StockResponseDto> getSearchAccumulationStocks(final SearchDto searchDto, final Pageable pageable);

  List<StockResponseDto> getStockListByNos(final Long[] stockNoArr);
  List<StockResponseDto> getNumOfStocksPerMaterial(final SearchDto searchDto);
  List<StockResponseDto> getStockListByStockNos(final Long[] stockNoArr);


}
