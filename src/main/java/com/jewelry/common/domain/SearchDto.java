package com.jewelry.common.domain;

import com.jewelry.common.paging.Pagination;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchDto extends Pagination {

  private String accessToken;
  private String menuId;
  private String tgtUserId;
  private String userId;

  private String searchDelYn = "N";
  private Long searchVender;
  private String searchType;
  private String searchWord;
  private String searchStdt;
  private String searchEddt;
  private String searchStore;
  private String searchStockType;

  private String searchUserRole;
  //코드관리 용 변수
  private Long customerNo;
  private Integer cdDepth;
  private String upCdId;

  private String searchMaterial;
  private String searchStep;
  private String orderStep;

  private String searchYear;
  private String searchSaleType;

  private String searchCashType;
  private String searchCashType2;
  private String searchBankbook;
  private String today;
  private String yesterday;
  private String befYesterday;

}
