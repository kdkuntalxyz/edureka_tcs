package com.deep.order.api;

import com.deep.order.domain.Order;
import com.deep.order.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class Api {

    @Autowired
    OrderService orderService;
    private static final Logger LOG = LoggerFactory.getLogger(Api.class);

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) throws JsonProcessingException {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping
    public List<Order> getAllOrder() {
        return orderService.getAllOrder();
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
    }
}
