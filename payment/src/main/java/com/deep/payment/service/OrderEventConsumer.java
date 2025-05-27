package com.deep.payment.service;

import com.deep.payment.domain.OrderEvent;
import com.deep.payment.domain.Payment;
import com.deep.payment.repo.Repo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PaymentEventProducer paymentEventProducer;
    @Autowired
    Repo repo;

    private static final Logger LOG = LoggerFactory.getLogger(OrderEventConsumer.class);

    @KafkaListener(topics = "order-events", groupId = "order-group")
    public void handleOrder(String message) throws JsonProcessingException {
        LOG.info("Received OrderEvent {}", message);
        OrderEvent event = objectMapper.readValue(message, OrderEvent.class);
        if ("ORDER_CREATED".equals(event.getEventType())) {
            LOG.info("Saving Payment frm order-events");
            Payment payment = Payment.builder()
                    .orderId(event.getOrderId())
                    .amount(event.getAmount())
                    .status("SUCCESS")
                    .build();

            repo.save(payment);

            LOG.info("Payment saved, posting PAYMENT_SUCCESS");

            // Emit event to inventory service
            event.setEventType("PAYMENT_SUCCESS");
            paymentEventProducer.sendPaymentSuccess(event);
        }
    }
}
