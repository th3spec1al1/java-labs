package ru.kurbanov.presentation.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.cars.enums.BodyType;
import ru.kurbanov.domain.entities.cars.enums.CarDrive;
import ru.kurbanov.domain.entities.cars.enums.GearboxType;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.engines.Engine;
import ru.kurbanov.presentation.dto.requests.CarRequestDto;
import ru.kurbanov.presentation.dto.responses.CarResponseDto;

import java.util.Map;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class CarDtoMapper {

    @Mapping(target = "enginePower", expression = "java(car.getEngine().power().value())")
    @Mapping(target = "engineDisplacement", expression = "java(car.getEngine().displacement().value())")
    @Mapping(target = "fuelType", expression = "java(car.getEngine().fuelType().name())")
    @Mapping(target = "carBody", expression = "java(car.getBody().name())")
    @Mapping(target = "wheelsId", expression = "java(car.getDetails().get(\"WHEELS\").getId())")
    @Mapping(target = "transmissionId", expression = "java(car.getDetails().get(\"TRANSMISSION\").getId())")
    @Mapping(target = "steeringWheelId", expression = "java(car.getDetails().get(\"STEERING_WHEEL\").getId())")
    @Mapping(target = "interiorId", expression = "java(car.getDetails().get(\"INTERIOR\").getId())")
    public abstract CarResponseDto toDto(Car car);

    public Car toDomain(UUID id, CarRequestDto dto,
                        Engine engine, BodyType body, CarDrive carDrive,
                        GearboxType gearboxType, Map<String, Detail> details) {
        if (dto == null) return null;
        return new Car(
                id != null ? id : UUID.randomUUID(),
                dto.getBrand(),
                dto.getModel(),
                engine,
                body,
                carDrive,
                gearboxType,
                details,
                dto.getColor(),
                dto.getPrice()
        );
    }
}