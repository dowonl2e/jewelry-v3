package com.jewelry.order.document;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OrderElasticsearchRepository extends ElasticsearchRepository<OrderSearchDoc, Long> {
}
