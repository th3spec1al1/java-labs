package ru.kurbanov.presentation.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.presentation.dto.requests.CarRequestDto;
import ru.kurbanov.presentation.dto.responses.CarResponseDto;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CarDtoMapper {

    @Mapping(target = "enginePower", expression = "java(car.getEngine().power().value())")
    @Mapping(target = "engineDisplacement", expression = "java(car.getEngine().displacement().value())")
    @Mapping(target = "fuelType", expression = "java(car.getEngine().fuelType().name())")
    @Mapping(target = "carBody", source = "body")
    @Mapping(target = "wheelsId", expression = "java(car.getDetails().get(\"Wheels\").getId())")
    @Mapping(target = "transmissionId", expression = "java(car.getDetails().get(\"Transmission\").getId())")
    @Mapping(target = "steeringWheelId", expression = "java(car.getDetails().get(\"SteeringWheel\").getId())")
    @Mapping(target = "interiorId", expression = "java(car.getDetails().get(\"Interior\").getId())")
    CarResponseDto toDto(Car car);

    @Mapping(target = "details", ignore = true)
    @Mapping(target = "engine", ignore = true)
    @Mapping(target = "body", ignore = true)
    @Mapping(target = "id", source = "id")
    Car toDomain(UUID id, CarRequestDto carRequestDto);
}
