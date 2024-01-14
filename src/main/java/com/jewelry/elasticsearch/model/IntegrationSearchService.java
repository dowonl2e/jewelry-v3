package com.jewelry.elasticsearch.model;

import com.jewelry.elasticsearch.document.IntegrationSearchDoc;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IntegrationSearchService {

  private final ElasticsearchOperations operations;

  public Map<String, Object> findAll(String keyword, Pageable pageable){
    Map<String, Object> response = new HashMap<>();

    QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(
        QueryBuilders.multiMatchQuery(
            keyword, "keyword.ngram"
        )
    ).filter(QueryBuilders.matchQuery("del_yn", "N"));

    NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
        .withPageable(pageable)
        .withQuery(queryBuilder)
        .build();

    SearchHits<IntegrationSearchDoc> searchHits = operations.search(nativeSearchQuery, IntegrationSearchDoc.class);
    Page<SearchHit<IntegrationSearchDoc>> orderPage = SearchHitSupport.searchPageFor(
        searchHits, pageable
    );

    List<IntegrationSearchDoc> docs = searchHits.stream()
        .map(SearchHit::getContent)
        .collect(Collectors.toList());

    response.put("list", docs.stream()
        .map(m -> m.toResponse())
        .collect(Collectors.toList())
    );

    response.put("keyword", keyword);

    return response;
  }
}
