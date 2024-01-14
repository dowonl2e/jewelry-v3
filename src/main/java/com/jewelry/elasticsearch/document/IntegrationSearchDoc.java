package com.jewelry.elasticsearch.document;

import com.jewelry.elasticsearch.dto.IntegerationSearchResponseDto;
import com.jewelry.order.dto.OrderResponseDto;
import com.jewelry.util.Utils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Document(indexName = "integration_search")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class IntegrationSearchDoc {

  @Id
  private String id;
  private String menu_link;
  private String menu_name;
  private String message_type;
  private String message;
  private String keyword;
  private String del_yn;
  private String updt_dt;
  private Long unix_ts_in_secs;

  public IntegerationSearchResponseDto toResponse(){
    return new IntegerationSearchResponseDto(
        this.id, this.menu_link, this.menu_name,
        this.message_type, this.message, this.keyword,
        this.del_yn, this.updt_dt, this.unix_ts_in_secs
    );
  }
}
