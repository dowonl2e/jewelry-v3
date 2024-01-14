package com.jewelry.elasticsearch.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class IntegerationSearchResponseDto {

  private String id;
  private String menu_link;
  private String menu_name;
  private String message_type;
  private String message;
  private String keyword;
  private String del_yn;
  private String updt_dt;
  private Long unix_ts_in_secs;

  public IntegerationSearchResponseDto(
      String id, String menu_link, String menu_name,
      String message_type, String message, String keyword,
      String del_yn, String updt_dt, Long unix_ts_in_secs
  ){
    this.id = id;
    this.menu_link = menu_link;
    this.menu_name = menu_name;
    this.message_type = message_type;
    this.message = message;
    this.keyword = keyword;
    this.del_yn = del_yn;
    this.updt_dt = updt_dt;
    this.unix_ts_in_secs = unix_ts_in_secs;
  }
}
