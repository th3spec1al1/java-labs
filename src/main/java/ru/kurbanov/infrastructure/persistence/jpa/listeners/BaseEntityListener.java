package ru.kurbanov.infrastructure.persistence.jpa.listeners;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import ru.kurbanov.infrastructure.persistence.jpa.model.BaseEntity;

import java.time.Instant;

public class BaseEntityListener {

    @PrePersist
    public void onPersist(BaseEntity entity) {
        Instant now = Instant.now();

        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @PreUpdate
    public void onUpdate(BaseEntity entity) {
        entity.setUpdatedAt(Instant.now());
    }
}
