package ru.kurbanov.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.factories.DetailFactory;
import ru.kurbanov.domain.entities.details.factories.model.InteriorFactory;
import ru.kurbanov.domain.entities.details.factories.model.SteeringWheelFactory;
import ru.kurbanov.domain.entities.details.factories.model.TransmissionFactory;
import ru.kurbanov.domain.entities.details.factories.model.WheelsFactory;
import ru.kurbanov.infrastructure.persistence.jpa.model.DetailEntity;

@Component
public class DetailEntityMapper {

    public Detail toDomain(DetailEntity detailEntity) {
        if (detailEntity == null) return null;

        DetailFactory detailFactory = switch (detailEntity.getType().toUpperCase()) {
            case "INTERIOR" -> new InteriorFactory();
            case "STEERING_WHEEL" -> new SteeringWheelFactory();
            case "TRANSMISSION" -> new TransmissionFactory();
            case "WHEELS" -> new WheelsFactory();
            default -> throw new IllegalArgumentException("Unknown detail type: " + detailEntity.getType());
        };

        return detailFactory.create(
                detailEntity.getId(),
                detailEntity.getName(),
                detailEntity.getPrice(),
                detailEntity.getCompatibleModels());
    }

    public DetailEntity toEntity(Detail detail) {
        if (detail == null) return null;

        DetailEntity detailEntity = new DetailEntity();
        detailEntity.setId(detail.getId());
        detailEntity.setName(detail.getName());
        detailEntity.setType(detail.getType());
        detailEntity.setPrice(detail.getPrice());
        detailEntity.setCompatibleModels(detail.getCompatibleModels());

        return detailEntity;
    }
}
