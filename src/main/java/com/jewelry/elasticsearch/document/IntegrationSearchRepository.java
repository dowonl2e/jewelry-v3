package com.jewelry.elasticsearch.document;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface IntegrationSearchRepository extends ElasticsearchRepository<IntegrationSearchDoc, String> {

}
