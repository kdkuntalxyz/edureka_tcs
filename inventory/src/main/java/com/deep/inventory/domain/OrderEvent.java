package com.deep.inventory.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@ToString
public class OrderEvent implements Serializable {
    private String orderId;
    private Integer customerId;
    private String productId;
    private int quantity;
    private double amount;
    private String eventType; // ORDER_CREATED, PAYMENT_COMPLETED
}
