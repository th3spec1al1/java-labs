package ru.kurbanov.infrastructure.persistence.jpa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.kurbanov.domain.entities.CarStatus;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "storage_cars")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "model_id", nullable = false)
    private UUID modelId;

    @Column(nullable = false)
    private String vin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(nullable = false)
    private boolean removed = false;
}
