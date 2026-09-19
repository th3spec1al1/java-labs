package ru.kurbanov.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kurbanov.infrastructure.persistence.jpa.model.OutboxEventEntity;

import java.util.List;
import java.util.UUID;

public interface JpaOutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID> {

    default List<OutboxEventEntity> findPendingBatch(int maxAttempts) {
        return findTop50ByPublishedAtIsNullAndAttemptsLessThanOrderByCreatedAtAsc(maxAttempts);
    }

    List<OutboxEventEntity> findTop50ByPublishedAtIsNullAndAttemptsLessThanOrderByCreatedAtAsc(int maxAttempts);
}