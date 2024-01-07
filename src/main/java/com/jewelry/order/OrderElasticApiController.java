package com.jewelry.order;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.config.provider.JwtTokenProvider;
import com.jewelry.order.model.OrderElasticsearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/api/elasticsearch/order")
@RequiredArgsConstructor
public class OrderElasticApiController {

  private final OrderElasticsearchService orderElasticsearchService;
  private final JwtTokenProvider jwtTokenProvider;
  private final String menuId = "order";

  @GetMapping("/list")
  public Map<String, Object> list(
      @RequestHeader("Authorization") String accessToken,
      final SearchDto searchDto){
    Pageable pageable = PageRequest.of(searchDto.getCurrentPage()-1, searchDto.getRecordCount());
    searchDto.setMenuId(menuId);
    searchDto.setUserId(jwtTokenProvider.getPrincipal(jwtTokenProvider.resolveToken(accessToken)));
    return orderElasticsearchService.findAll(searchDto, pageable);
  }
}
