package ru.kurbanov.application.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.kurbanov.application.abstractions.repositories.jpa.JpaOrderRepository;
import ru.kurbanov.application.events.OrderApprovedEvent;
import ru.kurbanov.application.events.OrderRejectedEvent;
import ru.kurbanov.infrastructure.persistence.jpa.model.OrderEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageEventConsumer {

    private final ObjectMapper objectMapper;
    private final JpaOrderRepository orderRepository;

    @KafkaListener(topics = "order-results", groupId = "order-service")
    @Transactional
    public void consume(String message) {
        try {
            JsonNode root = objectMapper.readTree(message);
            String eventType = detectEventType(root);
            MDC.put("traceId", root.path("traceId").asText());
            try {
                if ("OrderApproved".equals(eventType)) {
                    OrderApprovedEvent event = objectMapper.treeToValue(root, OrderApprovedEvent.class);
                    handleApproved(event);
                } else if ("OrderRejected".equals(eventType)) {
                    OrderRejectedEvent event = objectMapper.treeToValue(root, OrderRejectedEvent.class);
                    handleRejected(event);
                } else {
                    log.warn("Unknown event type in order-results topics");
                }
            } finally {
                MDC.remove("traceId");
            }
        } catch (Exception e) {
            log.error("Failed to process storage event", e);
            throw new RuntimeException(e);
        }
    }

    private void handleApproved(OrderApprovedEvent event) {
        log.info("Order approved: orderId={}", event.getOrderId());
        OrderEntity order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + event.getOrderId()));
        order.setOrderStatus("CAR_READY");
        orderRepository.save(order);
    }

    private void handleRejected(OrderRejectedEvent event) {
        log.info("Order rejected: orderId={}, reason={}", event.getOrderId(), event.getReason());
        OrderEntity order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + event.getOrderId()));
        order.setOrderStatus("CANCELLED");
        orderRepository.save(order);
    }

    private String detectEventType(JsonNode root) {
        if (root.has("reason")) {
            return "OrderRejected";
        }
        return "OrderApproved";
    }
}
