package ru.kurbanov.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kurbanov.domain.entities.CarStatus;
import ru.kurbanov.infrastructure.persistence.jpa.model.CarEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaCarRepository extends JpaRepository<CarEntity, UUID> {

    List<CarEntity> findByStatusAndRemovedFalse(CarStatus status);

    Optional<CarEntity> findByIdAndRemovedFalse(UUID id);
}
