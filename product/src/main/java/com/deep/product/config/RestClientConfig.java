package com.deep.product.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class RestClientConfig {

    @Value("${inventory.service.url}")
    private String baseUrl;
    @Value("${inventory.service.user}")
    private String user;
    @Value("${inventory.service.key}")
    private String key;

    @Bean
    public RestClient restClient(RestClient.Builder builder) {

        String basicAuthValue = "Basic " + Base64.getEncoder()
                .encodeToString((user + ":" + key).getBytes(StandardCharsets.UTF_8));
        return builder
                .baseUrl(baseUrl.concat("/inventories"))
                .defaultHeader("Authorization", basicAuthValue)
                .build();
    }
}
