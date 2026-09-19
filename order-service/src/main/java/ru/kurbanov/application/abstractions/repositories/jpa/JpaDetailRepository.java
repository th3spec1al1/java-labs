package ru.kurbanov.application.abstractions.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kurbanov.infrastructure.persistence.jpa.model.DetailEntity;

import java.util.UUID;

public interface JpaDetailRepository extends JpaRepository<DetailEntity, UUID> {
}
