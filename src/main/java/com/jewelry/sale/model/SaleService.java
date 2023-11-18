package com.jewelry.sale.model;

import com.jewelry.annotation.MenuAuthAnt;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.order.dto.OrderDto;
import com.jewelry.order.entity.OrderRepositoryImpl;
import com.jewelry.sale.dto.SaleDto;
import com.jewelry.sale.dto.SaleResponseDto;
import com.jewelry.sale.entity.SaleRepositoryImpl;
import com.jewelry.stock.dto.StockDto;
import com.jewelry.stock.entity.StockRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class SaleService {

  private final SaleRepositoryImpl saleRepositoryImpl;

  private final StockRepositoryImpl stockRepository;

  private final OrderRepositoryImpl orderRepository;
  @MenuAuthAnt
  public Map<String, Object> findAll(final SearchDto searchDto, final Pageable pageable){
    Map<String, Object> response = new HashMap<>();

    Page<SaleResponseDto> sales = saleRepositoryImpl.getSearchSales(searchDto, pageable);

    searchDto.setTotalPage(sales.getTotalPages());
    searchDto.setHasPrev(sales.hasPrevious());
    searchDto.setHasNext(sales.hasNext());
    searchDto.setTotalCount(sales.getTotalElements());

    response.put("list", sales.getContent());
    response.put("params", searchDto);

    return response;
  }

  @MenuAuthAnt
  public String deleteSalesToStock(final SaleDto saleDto){
    return saleRepositoryImpl.updateSalesToStock(saleDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateStocksSaleDate(final SaleDto saleDto){
    if(ObjectUtils.isEmpty(saleDto.getSaleArr())){
      return "fail";
    }
    int updateCnt = 0;
    List<Long> stockNoList = new ArrayList<>();
    List<Long> orderNoList = new ArrayList<>();

    for(String sale : saleDto.getSaleArr()) {
      String[] sales = sale.split("_");
      if(sales.length == 2) {
        if(sales[1].equals("STOCK")) stockNoList.add(Long.parseLong(sales[0]));
        if(sales[1].equals("ORDER")) orderNoList.add(Long.parseLong(sales[0]));
      }
    }

    if(stockNoList.size() > 0) {
      StockDto stockDto = new StockDto();
      stockDto.setStockNoArr(stockNoList.toArray(Long[]::new));
      stockDto.setSaleDt(saleDto.getSaleDt());
      stockDto.setUpdtId(saleDto.getUpdtId());
      updateCnt += stockRepository.updateStocksSaleDate(stockDto);
    }

    if(orderNoList.size() > 0) {
      OrderDto orderDto = new OrderDto();
      orderDto.setOrderNoArr(orderNoList.toArray(Long[]::new));
      orderDto.setReceiptDt(saleDto.getSaleDt());
      orderDto.setUpdtId(saleDto.getUpdtId());
      updateCnt += orderRepository.updateOrdersSaleDate(orderDto);
    }
    return updateCnt > 0 ? "success" : "fail";
  }
  @MenuAuthAnt
  public String updateSalesCustomer(final SaleDto saleDto){
    int res = 0;
    if(saleDto.getSaleArr() != null && saleDto.getSaleArr().length > 0) {
      List<Long> stockNoList = new ArrayList<>();
      List<Long> orderNoList = new ArrayList<>();

      for(String sale : saleDto.getSaleArr()) {
        String[] sales = sale.split("_");
        if(sales.length == 2) {
          if(sales[1].equals("STOCK")) stockNoList.add(Long.parseLong(sales[0]));
          if(sales[1].equals("ORDER")) orderNoList.add(Long.parseLong(sales[0]));
        }
      }

      if(stockNoList.size() > 0) {
        StockDto stockDto = new StockDto();
        stockDto.setStockNoArr(stockNoList.toArray(Long[]::new));
        stockDto.setCustomerNo(saleDto.getCustomerNo());
        stockDto.setCustomerNm(saleDto.getCustomerNm());
        stockDto.setUpdtId(saleDto.getUpdtId());
        res += stockRepository.updateStocksCustomer(stockDto);
      }

      if(orderNoList.size() > 0) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNoArr(orderNoList.toArray(Long[]::new));
        orderDto.setCustomerNo(saleDto.getCustomerNo());
        orderDto.setCustomerNm(saleDto.getCustomerNm());
        orderDto.setCustomerCel(saleDto.getCustomerCel());
        orderDto.setUpdtId(saleDto.getUpdtId());
        res += orderRepository.updateOrdersCustomer(orderDto);
      }

    }
    return res > 0 ? "success" : "fail";
  }
}
