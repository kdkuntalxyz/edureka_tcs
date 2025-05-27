package com.deep.product.config;

import com.deep.product.domain.InventoryClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class InventoryClient {

    @Autowired
    RestClient restClient;

    private static final Logger LOG = LoggerFactory.getLogger(InventoryClient.class);

    public int getQuantity(String productId) {
        try {
            LOG.info("getting product inventory quantities");
            return restClient.get()
                    .uri("/{productId}", productId)
                    .retrieve()
                    .body(InventoryClientResponse.class)
                    .getQuantity();
        } catch (RestClientException ex) {
            LOG.error("Error getting product inventory quantities"+ex);
            return 0; // default to 0 if inventory not found
        }
    }
}
