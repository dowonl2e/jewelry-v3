package com.jewelry.order.model;

import com.jewelry.annotation.MenuAuthAnt;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.order.document.OrderSearchDoc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderElasticsearchService {

  //private final ElasticsearchOperations elasticsearchOperations;

  @MenuAuthAnt
  public Map<String, Object> findAll(final SearchDto searchDto, final Pageable pageable){
    Map<String, Object> response = new HashMap<>();

//    QueryBuilder queryBuilder = null;
//
//    FieldSortBuilder sortBuilder = SortBuilders.fieldSort("order_no").order(SortOrder.DESC);
//
//    if(!ObjectUtils.isEmpty(searchDto.getSearchWord())) {
//      queryBuilder = QueryBuilders.multiMatchQuery(
//          searchDto.getSearchWord()
//          , "model_id.ngram", "customer_nm.ngram", "vender_nm.ngram"
//      );
//    }
//
//    NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//        .withQuery(queryBuilder)
//        .withPageable(pageable)
//        .withSort(sortBuilder)
//        .build();
//
//    SearchHits<OrderSearchDoc> searchHits = elasticsearchOperations.search(nativeSearchQuery, OrderSearchDoc.class);
//    Page<SearchHit<OrderSearchDoc>> orderPage = SearchHitSupport.searchPageFor(
//        searchHits, pageable
//    );
//
//    List<OrderSearchDoc> docs = searchHits.stream()
//        .map(SearchHit::getContent)
//        .collect(Collectors.toList());
//
//    searchDto.setTotalPage(orderPage.getTotalPages());
//    searchDto.setHasPrev(orderPage.hasPrevious());
//    searchDto.setHasNext(orderPage.hasNext());
//    searchDto.setTotalCount(orderPage.getTotalElements());
//
//    response.put("list", docs.stream()
//        .map(m -> m.toResponse())
//        .collect(Collectors.toList())
//    );
    response.put("params", searchDto);

    return response;
  }
}
