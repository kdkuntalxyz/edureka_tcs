package com.deep.order.config;

import com.deep.order.domain.OrderEvent;
import com.deep.order.service.OrderEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockKafkaConfig {

    @Bean
    public OrderEventProducer orderEventProducer() {
        return new OrderEventProducer() {
            @Override
            public void sendEvent(OrderEvent event) throws JsonProcessingException {
                // Mock Kafka: do nothing
                System.out.println("Mock Kafka Event: " + event);
            }
        };
    }
}
