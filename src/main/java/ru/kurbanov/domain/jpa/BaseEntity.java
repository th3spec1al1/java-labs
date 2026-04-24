package ru.kurbanov.domain.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "TIMESTAMP", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "created_time", columnDefinition = "TIMESTAMP", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_time", columnDefinition = "TIMESTAMP", nullable = false)
    private Instant updatedAt;

    @Column(name = "removed", columnDefinition = "TIMESTAMP", nullable = false)
    private boolean removed = false;
}
