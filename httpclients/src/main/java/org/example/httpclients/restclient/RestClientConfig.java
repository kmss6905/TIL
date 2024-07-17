package org.example.httpclients.restclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  @Bean
  RestClient restClient() {
    return RestClient.builder()
        .requestFactory(new SimpleClientHttpRequestFactory())
        .baseUrl("https://open.er-api.com/v6/latest")
        .build();
  }
}
