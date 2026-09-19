package ru.kurbanov.application.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kurbanov.infrastructure.persistence.jpa.JpaOutboxEventRepository;
import ru.kurbanov.infrastructure.persistence.jpa.model.OutboxEventEntity;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxEventService {

    private final JpaOutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void save(UUID aggregateId, String eventType, Object payload, String traceId) {
        try {
            String json = objectMapper.writeValueAsString(payload);
            OutboxEventEntity event = new OutboxEventEntity();
            event.setEventId(UUID.randomUUID());
            event.setAggregateId(aggregateId);
            event.setEventType(eventType);
            event.setTraceId(traceId);
            event.setPayload(json);
            event.setAttempts(0);
            outboxEventRepository.save(event);
            log.info("Outbox event saved: type={}, aggregateId={}, traceId={}", eventType, aggregateId, traceId);
        } catch (Exception e) {
            log.error("Failed to save outbox event", e);
            throw new RuntimeException("Failed to save outbox event", e);
        }
    }
}
