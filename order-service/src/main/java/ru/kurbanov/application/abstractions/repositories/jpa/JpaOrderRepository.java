package ru.kurbanov.application.abstractions.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.kurbanov.infrastructure.persistence.jpa.model.OrderEntity;

import java.util.UUID;

public interface JpaOrderRepository extends JpaRepository<OrderEntity, UUID>, JpaSpecificationExecutor<OrderEntity> {
}
