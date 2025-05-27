package com.deep.order.service;
import com.deep.order.domain.OrderEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public void sendEvent(OrderEvent event) throws JsonProcessingException {
        kafkaTemplate.send("order-events", objectMapper.writeValueAsString(event));
    }
}
