package com.jewelry.main.model;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.order.entity.OrderRepositoryImpl;
import com.jewelry.sale.dto.SaleResponseDto;
import com.jewelry.sale.entity.SaleRepositoryImpl;
import com.jewelry.stock.entity.StockRepositoryImpl;
import com.jewelry.util.Utils;
import lombok.RequiredArgsConstructor;
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
public class MainService {

  private final SaleRepositoryImpl saleRepository;
  private final OrderRepositoryImpl orderRepository;
  private final StockRepositoryImpl stockRepository;

  public Map<String, Object> findAllStats(final SearchDto searchDto) {
    Map<String, Object> response = new HashMap<>();

    searchDto.setSearchYear(Utils.getTodayDateFormat("yyyy"));

    //월별 매출 현황
    response.put("salePriceMonthly", getSaleMonthlyPriceList(searchDto));

    //재질별 주문개수
		response.put("materialOrders", orderRepository.getNumOfOrdersPerMaterial(searchDto));

    //재질별 현 재고현황
		response.put("materialStocks", stockRepository.getNumOfStocksPerMaterial(searchDto));
    return response;
  }

	public Map<String, Object> findMonthlySalePrice(final SearchDto searchDto) {
		Map<String, Object> response = new HashMap<>();
		response.put("salePriceMonthly", getSaleMonthlyPriceList(searchDto));
		return response;
	}

	private List<Integer> getSaleMonthlyPriceList(final SearchDto searchDto){
		List<Integer> salePriceList = new ArrayList<>();
		List<SaleResponseDto> monthSaleList = saleRepository.getMonthlySalePriceStats(searchDto);
		if(monthSaleList == null || monthSaleList.size() == 0) {
			for(int i = 0 ; i < 12 ; i++) salePriceList.add(0);
		}
		else {
			for(int i = 0 ; i < 12 ; i++) {
				int salePrice = 0;
				for(SaleResponseDto monthvo : monthSaleList) {
					if(!ObjectUtils.isEmpty(monthvo.getSaleMonth()) && monthvo.getSaleMonth() == (i+1)) {
						salePrice = monthvo.getSalePrice();
						break;
					}
				}
				salePriceList.add(salePrice);
			}
		}
		return salePriceList;
	}

	//재질별 주문개수
	public Map<String, Object> findMaterialOrders(final SearchDto searchDto) {
		Map<String, Object> response = new HashMap<>();
		response.put("materialOrders", orderRepository.getNumOfOrdersPerMaterial(searchDto));
		return response;
	}

	//재질별 주문개수
	public Map<String, Object> findMaterialStocks(final SearchDto searchDto) {
		Map<String, Object> response = new HashMap<>();
		response.put("materialStocks", stockRepository.getNumOfStocksPerMaterial(searchDto));
		return response;

	}
}
