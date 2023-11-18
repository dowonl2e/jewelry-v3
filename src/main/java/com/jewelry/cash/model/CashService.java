package com.jewelry.cash.model;

import com.jewelry.annotation.MenuAuthAnt;
import com.jewelry.cash.dto.CashDto;
import com.jewelry.cash.dto.CashResponseDto;
import com.jewelry.cash.entity.CashRepository;
import com.jewelry.cash.entity.CashRepositoryImpl;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CashService {

  private final CashRepository cashRepository;
  private final CashRepositoryImpl cashRepositoryImpl;

  @MenuAuthAnt
  public Map<String,Object> findAll(final SearchDto searchDto, final Pageable pageable){
    Map<String, Object> response = new HashMap<>();

    Page<CashResponseDto> cashes = cashRepositoryImpl.getSearchCashes(searchDto, pageable);

    searchDto.setTotalPage(cashes.getTotalPages());
    searchDto.setHasPrev(cashes.hasPrevious());
    searchDto.setHasNext(cashes.hasNext());
    searchDto.setTotalCount(cashes.getTotalElements());

    searchDto.setToday(ObjectUtils.isEmpty(searchDto.getSearchEddt()) ? Utils.getTodayDateFormat("yyyy-MM-dd") : searchDto.getSearchEddt());
    searchDto.setYesterday(Utils.getDateCalDateFormat(searchDto.getToday(), "yyyy-MM-dd", -1));
    searchDto.setBefYesterday(Utils.getDateCalDateFormat(searchDto.getYesterday(), "yyyy-MM-dd", -1));
    List<CashResponseDto> stats = cashRepositoryImpl.getCashStatsList(searchDto, pageable);
    stats = stats == null ? new ArrayList<>() : stats;

    List<CashResponseDto> matStats = cashRepositoryImpl.getCashMaterialStatsList(searchDto, pageable);
    if(!CollectionUtils.isEmpty(matStats))
      for(CashResponseDto item : matStats) stats.add(item);

    response.put("list", cashes.getContent());
    response.put("stats", stats);
    response.put("today", searchDto.getToday());
    response.put("yesterday", searchDto.getYesterday());
    response.put("params", searchDto);

    return response;
  }

  @MenuAuthAnt
  public CashResponseDto findCash(final CashDto cashDto) {
    return cashRepository.getCash(cashDto.getCashNo());
  }

  @MenuAuthAnt
  public String save(CashDto cashDto) {
    int resultCnt = 0;
    if(cashDto.getCashTypeCdArr() != null && cashDto.getCashTypeCdArr().length > 0) {
      String[] cashTypeCdArr = cashDto.getCashTypeCdArr();
      String[] cashTypeCd2Arr = cashDto.getCashTypeCd2Arr();
      String[] bankbookCdArr = cashDto.getBankbookCdArr();
      String[] venderNmArr = cashDto.getVenderNmArr();
      String[] historyDescArr = cashDto.getHistoryDescArr();
      String[] materialCdArr = cashDto.getMaterialCdArr();
      Double[] weightGramArr = cashDto.getWeightGramArr();
      Integer[] quantityArr = cashDto.getQuantityArr();
      Integer[] unitPriceArr = cashDto.getUnitPriceArr();

      for(int i = 0 ; i < cashTypeCdArr.length ; i++) {
        if(!ObjectUtils.isEmpty(cashTypeCdArr[i])) {
          cashDto.setCashTypeCd(cashTypeCdArr[i]);
          cashDto.setCashTypeCd2(cashTypeCd2Arr[i]);
          cashDto.setBankbookCd(bankbookCdArr[i]);
          cashDto.setVenderNm(venderNmArr[i]);
          cashDto.setHistoryDesc(historyDescArr[i]);
          cashDto.setMaterialCd(materialCdArr[i]);
          cashDto.setWeightGram(weightGramArr[i]);
          cashDto.setUnitPrice(unitPriceArr[i]);
          cashDto.setQuantity(1);

          int quantity = quantityArr[i] == null ? 0 : quantityArr[i];
          for(int j = 0 ; j < quantity ; j++) {
            Long cashNo = cashRepository.save(cashDto.toEntity()).getCashNo();
            resultCnt += (cashNo != null && cashNo > 0 ? 1 : 0);
          }
        }
      }
    }
    return resultCnt > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String update(final CashDto cashDto) {
    int resultCnt = 0;
    if(cashDto.getCashTypeCdArr() != null && cashDto.getCashTypeCdArr().length > 0) {
      String[] cashTypeCdArr = cashDto.getCashTypeCdArr();
      String[] cashTypeCd2Arr = cashDto.getCashTypeCd2Arr();
      String[] bankbookCdArr = cashDto.getBankbookCdArr();
      String[] venderNmArr = cashDto.getVenderNmArr();
      String[] historyDescArr = cashDto.getHistoryDescArr();
      String[] materialCdArr = cashDto.getMaterialCdArr();
      Double[] weightGramArr = cashDto.getWeightGramArr();
      Integer[] quantityArr = cashDto.getQuantityArr();
      Integer[] unitPriceArr = cashDto.getUnitPriceArr();

      cashDto.setCashTypeCd(cashTypeCdArr[0]);
      cashDto.setCashTypeCd2(cashTypeCd2Arr[0]);
      cashDto.setBankbookCd(bankbookCdArr[0]);
      cashDto.setVenderNm(venderNmArr[0]);
      cashDto.setHistoryDesc(historyDescArr[0]);
      cashDto.setMaterialCd(materialCdArr[0]);
      cashDto.setWeightGram(weightGramArr[0]);
      cashDto.setUnitPrice(unitPriceArr[0]);
      cashDto.setQuantity(1);
      resultCnt += cashRepositoryImpl.updateCash(cashDto);

      for(int i = 0 ; i < cashTypeCdArr.length ; i++) {
        if(!ObjectUtils.isEmpty(cashTypeCdArr[i])) {
          cashDto.setCashNo(null);
          cashDto.setCashTypeCd(cashTypeCdArr[i]);
          cashDto.setCashTypeCd2(cashTypeCd2Arr[i]);
          cashDto.setBankbookCd(bankbookCdArr[i]);
          cashDto.setVenderNm(venderNmArr[i]);
          cashDto.setHistoryDesc(historyDescArr[i]);
          cashDto.setMaterialCd(materialCdArr[i]);
          cashDto.setWeightGram(weightGramArr[i]);
          cashDto.setUnitPrice(unitPriceArr[i]);
          cashDto.setQuantity(1);

          boolean multiInsertCheck = false;
          for(int idx = 1 ; idx < cashTypeCdArr.length ; idx++) {
            if(cashTypeCdArr[idx] != null) {
              multiInsertCheck = true;
              break;
            }
          }

          if((quantityArr[0] != null && quantityArr[0] > 1) || multiInsertCheck == true) {
            int quantity = quantityArr[i] == null ? 0 : quantityArr[i];
            if(i == 0)
              quantity = quantity <= 1 ? 0 : (quantity-1);

            for(int j = 0 ; j < quantity ; j++) {
              Long cashNo = cashRepository.save(cashDto.toEntity()).getCashNo();
              resultCnt += (cashNo != null && cashNo > 0 ? 1 : 0);
            }
          }
        }
      }
    }

    return resultCnt > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateCashToDelete(final CashDto cashDto) {
    return cashRepositoryImpl.updateCashToDelete(cashDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateCashesToDelete(final CashDto cashDto) {
    return cashRepositoryImpl.updateCashesToDelete(cashDto) > 0 ? "success" : "fail";
  }
}
