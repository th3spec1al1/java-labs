package ru.kurbanov.application.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.kurbanov.application.events.OrderApprovedEvent;
import ru.kurbanov.application.events.OrderSentForApprovalEvent;
import ru.kurbanov.infrastructure.persistence.jpa.JpaAssemblyOrderRepository;
import ru.kurbanov.infrastructure.persistence.jpa.model.AssemblyOrderEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventConsumer {

    private static final String CONSUMER_NAME = "storage-service-order-events";

    private final ObjectMapper objectMapper;
    private final JpaAssemblyOrderRepository assemblyOrderRepository;
    private final ProcessedMessageService processedMessageService;
    private final OutboxEventService outboxEventService;

    @KafkaListener(topics = "order-events", groupId = "storage-service")
    public void consume(String message) {
        OrderSentForApprovalEvent event;
        try {
            event = objectMapper.readValue(message, OrderSentForApprovalEvent.class);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to deserialize order event", e);
        }

        MDC.put("traceId", event.getTraceId());
        try {
            log.info("Received OrderSentForApproval: orderId={} eventId={}", event.getOrderId(), event.getEventId());
            processedMessageService.processIfNew(
                    event.getEventId(),
                    "OrderSentForApproval",
                    CONSUMER_NAME,
                    () -> handle(event)
            );
        } finally {
            MDC.remove("traceId");
        }
    }

    private void handle(OrderSentForApprovalEvent event) {
        AssemblyOrderEntity assemblyOrder = new AssemblyOrderEntity();
        assemblyOrder.setSourceOrderId(event.getOrderId());
        assemblyOrder.setSourceOrderType(event.getOrderType());
        assemblyOrder.setCarId(event.getCarId());
        assemblyOrder.setModelId(event.getModelId());
        assemblyOrder.setRequiredComponentIds(event.getRequiredComponentIds());
        assemblyOrder.setStatus("CREATED");
        assemblyOrderRepository.save(assemblyOrder);

        OrderApprovedEvent approvedEvent = new OrderApprovedEvent(
                event.getOrderId(),
                event.getTraceId()
        );
        outboxEventService.save(event.getOrderId(), "order-results", approvedEvent, event.getTraceId());
        assemblyOrder.setStatus("ASSEMBLED");
        log.info("Assembly order created and approved: orderId={}", event.getOrderId());
    }
}
