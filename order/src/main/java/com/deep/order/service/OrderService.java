package com.deep.order.service;

import com.deep.order.domain.Order;
import com.deep.order.domain.OrderEvent;
import com.deep.order.repo.Repo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    Repo repo;
    @Autowired
    OrderEventProducer producer;

    public Order createOrder(Order order) throws JsonProcessingException {
        order.setStatus("CREATED");
        Order saved = repo.save(order);

        OrderEvent event = OrderEvent.builder()
                .orderId(saved.getId())
                .customerId(saved.getCustomerId())
                .productId(saved.getProductId())
                .quantity(saved.getQuantity())
                .amount(saved.getAmount())
                .eventType("ORDER_CREATED")
                .build();

        producer.sendEvent(event);
        return saved;
    }

    public List<Order> getAllOrder() {
        return repo.findAll();
    }

    public void deleteOrder(String id) {
        repo.deleteById(id);
    }
}
