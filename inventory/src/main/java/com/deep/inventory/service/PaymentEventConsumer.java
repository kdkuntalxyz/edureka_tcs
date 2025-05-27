package com.deep.inventory.service;

import com.deep.inventory.domain.Inventory;
import com.deep.inventory.domain.OrderEvent;
import com.deep.inventory.repo.Repo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentEventConsumer {

    @Autowired
    Repo repo;
    @Autowired
    ObjectMapper objectMapper;

    private static final Logger LOG = LoggerFactory.getLogger(PaymentEventConsumer.class);

    @KafkaListener(topics = "payment-events", groupId = "payment-group")
    public void handlePaymentSuccess(String message) throws JsonProcessingException {
        LOG.info("Received PaymentEvent {}", message);
        OrderEvent event = objectMapper.readValue(message, OrderEvent.class);
        if ("PAYMENT_SUCCESS".equals(event.getEventType())) {
            Optional<Inventory> optional = repo.findByProductId(event.getProductId());
            if (optional.isPresent()) {
                Inventory inventory = optional.get();
                int updatedQty = inventory.getQuantity() - event.getQuantity();
                if (updatedQty >= 0) {
                    inventory.setQuantity(updatedQty);
                    repo.save(inventory);
                    LOG.info("Inventory updated for product " + event.getProductId());
                } else {
                    LOG.info("Not enough inventory for product " + event.getProductId());
                }
            } else {
                LOG.info("No inventory found for product " + event.getProductId());
            }
        }
    }
}
