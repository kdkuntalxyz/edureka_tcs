package com.deep.payment.service;

import com.deep.payment.domain.OrderEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventProducer {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public void sendPaymentSuccess(OrderEvent event) throws JsonProcessingException {
        kafkaTemplate.send("payment-events", objectMapper.writeValueAsString(event));
    }
}
