package ru.kurbanov.application.abstractions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kurbanov.infrastructure.persistence.jpa.model.DetailEntity;

import java.util.UUID;

public interface DetailRepository extends JpaRepository<DetailEntity, UUID> {
}
