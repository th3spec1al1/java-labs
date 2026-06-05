package ru.kurbanov.presentation.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.*;
import ru.kurbanov.presentation.dto.requests.DetailRequestDto;
import ru.kurbanov.presentation.dto.responses.DetailResponseDto;

import java.util.UUID;

@Mapper(componentModel = "spring")
public class DetailDtoMapper {

    public DetailResponseDto toDto(Detail detail) {
        if (detail == null) return null;

        return new DetailResponseDto(
                detail.getId(), detail.getType(), detail.getName(),
                detail.getPrice(), detail.getCompatibleModels());
    }

    public Detail toDomain(UUID id, DetailRequestDto detailRequestDto) {
        if (detailRequestDto == null) return null;

        UUID detailId = id != null ? id : UUID.randomUUID();

        return switch (detailRequestDto.getType().toUpperCase()) {
            case "INTERIOR" -> new Interior(
                    detailId,
                    detailRequestDto.getName(),
                    detailRequestDto.getPrice(),
                    detailRequestDto.getCompatibleModels()
            );
            case "STEERING_WHEEL" -> new SteeringWheel(
                    detailId,
                    detailRequestDto.getName(),
                    detailRequestDto.getPrice(),
                    detailRequestDto.getCompatibleModels()
            );
            case "TRANSMISSION" -> new Transmission(
                    detailId,
                    detailRequestDto.getName(),
                    detailRequestDto.getPrice(),
                    detailRequestDto.getCompatibleModels()
            );
            case "WHEELS" -> new Wheels(
                    detailId,
                    detailRequestDto.getName(),
                    detailRequestDto.getPrice(),
                    detailRequestDto.getCompatibleModels()
            );
            default -> throw new IllegalArgumentException(
                    "Unknown detail type: " + detailRequestDto.getType()
            );
        };
    }
}
