package com.deep.product.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAvailabilityResponse {

    private String id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private int quantity;
}
