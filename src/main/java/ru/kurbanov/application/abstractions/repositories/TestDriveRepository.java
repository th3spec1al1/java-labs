package ru.kurbanov.application.abstractions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kurbanov.infrastructure.persistence.jpa.model.TestDriveEntity;

import java.util.UUID;

public interface TestDriveRepository extends JpaRepository<TestDriveEntity, UUID> {
}
