package ru.kurbanov.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kurbanov.infrastructure.persistence.jpa.model.AssemblyOrderEntity;

import java.util.UUID;

public interface JpaAssemblyOrderRepository extends JpaRepository<AssemblyOrderEntity, UUID> {
}