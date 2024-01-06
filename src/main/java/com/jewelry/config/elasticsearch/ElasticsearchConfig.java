package com.jewelry.config.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

  @Value("${spring.elasticsearch.host}")
  private String host;

  @Value("${spring.elasticsearch.port}")
  private int port;

  @Value("${spring.elasticsearch.username}")
  private String username;
  @Value("${spring.elasticsearch.password}")
  private String password;

  @Override
  public RestHighLevelClient elasticsearchClient() {

    ClientConfiguration clientConfiguration = ClientConfiguration.builder()
        .connectedTo(host+":"+port)
        .withBasicAuth(username, password)
        .build();

    return RestClients.create(clientConfiguration).rest();
  }

  /**
   * Elasticsearch Rest Template 빈 등록
   */
//  @Bean
//  public ElasticsearchOperations elasticsearchOperations(){
//    return new ElasticsearchRestTemplate(elasticsearchClient());
//  }
}
