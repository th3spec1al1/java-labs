package ru.kurbanov.application.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kurbanov.application.abstractions.repositories.jpa.JpaDetailRepository;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.infrastructure.persistence.jpa.model.CarEntity;
import ru.kurbanov.infrastructure.persistence.mappers.DetailEntityMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CarDetailsLoader {

    private final JpaDetailRepository detailRepository;
    private final DetailEntityMapper detailEntityMapper;

    public Detail loadDetail(UUID detailId) {
        return detailRepository.findById(detailId)
                .map(detailEntityMapper::toDomain)
                .orElseThrow(() -> new EntityNotFoundException("Detail not found: " + detailId));
    }

    public Map<String, Detail> loadDetails(CarEntity carEntity) {
        Map<String, Detail> details = new HashMap<>();
        details.put("WHEELS", loadDetail(carEntity.getWheelsId()));
        details.put("TRANSMISSION", loadDetail(carEntity.getTransmissionId()));
        details.put("STEERING_WHEEL", loadDetail(carEntity.getSteeringWheelId()));
        details.put("INTERIOR", loadDetail(carEntity.getInteriorId()));
        return details;
    }
}
