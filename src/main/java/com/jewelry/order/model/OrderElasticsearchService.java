package com.jewelry.order.model;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.jewelry.annotation.MenuAuthAnt;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.order.document.OrderElasticsearchRepository;
import com.jewelry.order.document.OrderSearchDoc;
import com.jewelry.order.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderElasticsearchService {

  private final ElasticsearchOperations elasticsearchOperations;

  @MenuAuthAnt
  public Map<String, Object> findAll(final SearchDto searchDto, final Pageable pageable){
    Map<String, Object> response = new HashMap<>();
    if(ObjectUtils.isEmpty(searchDto.getSearchWord())){
      return response;
    }

    FieldSortBuilder sortBuilder = SortBuilders.fieldSort("order_no").order(SortOrder.DESC);
    MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(
        searchDto.getSearchWord()
        , "model_id.ngram", "customer_nm.ngram", "vender_nm.ngram"
    );

    NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
        .withQuery(multiMatchQueryBuilder)
        .withPageable(pageable)
        .withSort(sortBuilder)
        .build();

    SearchHits<OrderSearchDoc> orders = elasticsearchOperations.search(nativeSearchQuery, OrderSearchDoc.class);
    Page<SearchHit<OrderSearchDoc>> orderPage = SearchHitSupport.searchPageFor(
        orders, pageable
    );

    List<OrderResponseDto> content = new ArrayList<>();
    if(orders != null){
      List<SearchHit<OrderSearchDoc>> hits = orders.getSearchHits();
      if(hits != null){
        for(SearchHit<OrderSearchDoc> hit : hits){
          content.add(hit.getContent().toResponse());
        }
      }
    }

    searchDto.setTotalPage(orderPage.getTotalPages());
    searchDto.setHasPrev(orderPage.hasPrevious());
    searchDto.setHasNext(orderPage.hasNext());
    searchDto.setTotalCount(orderPage.getTotalElements());

    response.put("list", content);
    response.put("params", searchDto);

    return response;
  }
}
