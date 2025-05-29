package com.deep.product.service;

import com.deep.product.config.InventoryClient;
import com.deep.product.domain.Product;
import com.deep.product.domain.ProductAvailabilityResponse;
import com.deep.product.repo.Repo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    Repo repo;
    @Autowired
    InventoryClient inventoryClient;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @CircuitBreaker(name = "productServiceCB", fallbackMethod = "getDefaultProduct")
    public ProductAvailabilityResponse getProduct(@PathVariable String id) {
        return repo.findById(id)
                .map(product -> {
                    int quantity = inventoryClient.getQuantity(id);
                    return ProductAvailabilityResponse.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .price(product.getPrice())
                            .description(product.getDescription())
                            .category(product.getCategory())
                            .quantity(quantity)
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public ProductAvailabilityResponse getDefaultProduct(String id, Throwable t) {
        return ProductAvailabilityResponse.builder()
                .name("Fallback Product")
                .description("Fallback Product - Service unavailable")
                .quantity(0)
                .build();
    }

    public Product addProduct(Product product) {
        return repo.save(product);
    }

    public void deleteProduct(String id) {
        repo.deleteById(id);
    }

}
