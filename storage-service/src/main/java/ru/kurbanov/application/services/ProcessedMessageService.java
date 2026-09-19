package ru.kurbanov.application.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kurbanov.infrastructure.persistence.jpa.JpaProcessedMessageRepository;
import ru.kurbanov.infrastructure.persistence.jpa.model.ProcessedMessageEntity;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcessedMessageService {

    private final JpaProcessedMessageRepository repository;

    @Transactional
    public void processIfNew(UUID eventId, String eventType, String consumerName, Runnable handler) {
        if (repository.existsByEventIdAndConsumerName(eventId, consumerName)) {
            return;
        }
        handler.run();
        ProcessedMessageEntity entity = new ProcessedMessageEntity();
        entity.setId(UUID.randomUUID());
        entity.setEventId(eventId);
        entity.setEventType(eventType);
        entity.setConsumerName(consumerName);
        entity.setProcessed_at(Instant.now());
        repository.save(entity);
    }
}
