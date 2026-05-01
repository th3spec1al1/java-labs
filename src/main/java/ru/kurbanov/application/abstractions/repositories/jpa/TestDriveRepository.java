package ru.kurbanov.application.abstractions.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kurbanov.infrastructure.persistence.jpa.model.TestDriveEntity;

import java.util.UUID;

public interface TestDriveRepository extends JpaRepository<TestDriveEntity, UUID> {
}
