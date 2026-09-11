package ru.kurbanov.application.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kurbanov.infrastructure.persistence.jpa.JpaOutboxEventRepository;
import ru.kurbanov.infrastructure.persistence.jpa.model.OutboxEventEntity;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxPublisher {

    private static final int MAX_ATTEMPTS = 5;

    private final JpaOutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publishPendingEvents() {
        List<OutboxEventEntity> events = outboxEventRepository.findPendingBatch(MAX_ATTEMPTS);

        for (OutboxEventEntity event : events) {
            try {
                if (event.getTraceId() != null) {
                    MDC.put("traceId", event.getTraceId());
                }
                kafkaTemplate.send(event.getEventType(), event.getAggregateId().toString(), event.getPayload());
                event.setPublishedAt(Instant.now());
                log.info("Published outbox event: type={}, aggregateId={}",
                        event.getEventType(), event.getAggregateId());
            } catch (Exception e) {
                event.setAttempts(event.getAttempts() + 1);
                event.setLastError(e.getMessage());
                log.error("Failed to publish outbox event: id={}, attempt={}", event.getId(), event.getAttempts(), e);
            } finally {
                MDC.remove("traceId");
            }
        }
    }
}