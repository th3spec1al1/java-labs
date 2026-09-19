package ru.kurbanov.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kurbanov.infrastructure.persistence.jpa.model.ProcessedMessageEntity;

import java.util.UUID;

public interface JpaProcessedMessageRepository extends JpaRepository<ProcessedMessageEntity, UUID> {
    boolean existsByEventIdAndConsumerName(UUID eventId, String consumerName);
}
