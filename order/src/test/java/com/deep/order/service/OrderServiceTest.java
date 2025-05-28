package com.deep.order.service;

import com.deep.order.domain.Order;
import com.deep.order.domain.OrderEvent;
import com.deep.order.repo.Repo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private Repo repo;

    @Mock
    private OrderEventProducer producer;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = Order.builder()
                .id("order-1")
                .customerId(2)
                .productId("prod-1")
                .quantity(2)
                .amount(100.0)
                .build();
    }

    @Test
    void testCreateOrder_Success() throws JsonProcessingException {
        // Arrange
        when(repo.save(any(Order.class))).thenAnswer(invocation -> {
            Order o = invocation.getArgument(0);
            o.setId("order-1");  // simulate MongoDB setting the ID
            return o;
        });

        // Act
        Order result = orderService.createOrder(order);

        // Assert
        assertNotNull(result);
        assertEquals("order-1", result.getId());
        assertEquals("CREATED", result.getStatus());

        // Verify interactions
        verify(repo, times(1)).save(any(Order.class));
        verify(producer, times(1)).sendEvent(any(OrderEvent.class));
    }

    @Test
    void testGetAllOrder() {
        // Arrange
        List<Order> orders = List.of(order);
        when(repo.findAll()).thenReturn(orders);

        // Act
        List<Order> result = orderService.getAllOrder();

        // Assert
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getCustomerId());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testDeleteOrder() {
        // Act
        orderService.deleteOrder("order-1");

        // Assert
        verify(repo, times(1)).deleteById("order-1");
    }
}
