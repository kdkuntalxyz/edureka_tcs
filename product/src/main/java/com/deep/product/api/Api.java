package com.deep.product.api;

import com.deep.product.config.InventoryClient;
import com.deep.product.domain.Product;
import com.deep.product.domain.ProductAvailabilityResponse;
import com.deep.product.repo.Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class Api {

    private static final Logger LOG = LoggerFactory.getLogger(Api.class);

    @Autowired
    Repo repo;
    @Autowired
    InventoryClient inventoryClient;

    @GetMapping
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductAvailabilityResponse> getProduct(@PathVariable String id) {
        return repo.findById(id)
                .map(product -> {
                    int quantity = inventoryClient.getQuantity(id);
                    return ResponseEntity.ok(ProductAvailabilityResponse.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .price(product.getPrice())
                            .description(product.getDescription())
                            .category(product.getCategory())
                            .quantity(quantity)
                            .build());
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return repo.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        repo.deleteById(id);
    }
}
