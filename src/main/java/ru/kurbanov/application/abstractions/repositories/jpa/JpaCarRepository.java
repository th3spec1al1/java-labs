package ru.kurbanov.application.abstractions.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.kurbanov.infrastructure.persistence.jpa.model.CarEntity;

import java.util.UUID;

public interface JpaCarRepository extends JpaRepository<CarEntity, UUID>, JpaSpecificationExecutor<CarEntity> {
}
