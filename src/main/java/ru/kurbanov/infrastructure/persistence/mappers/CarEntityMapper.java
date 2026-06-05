package ru.kurbanov.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.engines.Engine;
import ru.kurbanov.domain.vo.Displacement;
import ru.kurbanov.domain.vo.Power;
import ru.kurbanov.infrastructure.persistence.jpa.model.CarEntity;

import java.util.Map;

@Component
public class CarEntityMapper {

    public Car toDomain(CarEntity carEntity, Map<String, Detail> details) {
        if (carEntity == null) return null;

        Engine engine = new Engine(
                new Power(carEntity.getEnginePower()),
                new Displacement(carEntity.getEngineDisplacement()),
                carEntity.getFuelType());

        return new Car(
                carEntity.getId(),
                carEntity.getBrand(),
                carEntity.getModel(),
                engine,
                carEntity.getCarBody(),
                carEntity.getCarDrive(),
                carEntity.getGearboxType(),
                details,
                carEntity.getColor(),
                carEntity.getPrice()
        );
    }

    public CarEntity toEntity(Car car) {
        if (car == null) return null;

        CarEntity carEntity = new CarEntity();
        
        carEntity.setId(car.getId());
        carEntity.setBrand(car.getBrand());
        carEntity.setModel(car.getModel());

        Engine engine = car.getEngine();
        carEntity.setEnginePower(engine.power().value());
        carEntity.setEngineDisplacement(engine.displacement().value());
        carEntity.setFuelType(engine.fuelType());

        carEntity.setCarBody(car.getBody());
        carEntity.setCarDrive(car.getCarDrive());
        carEntity.setGearboxType(car.getGearboxType());

        Map<String, Detail> details = car.getDetails();
        carEntity.setWheelsId(details.get("WHEELS").getId());
        carEntity.setTransmissionId(details.get("TRANSMISSION").getId());
        carEntity.setSteeringWheelId(details.get("STEERING_WHEEL").getId());
        carEntity.setInteriorId(details.get("INTERIOR").getId());

        carEntity.setColor(car.getColor());
        carEntity.setPrice(car.getBasePrice());
        return carEntity;
    }
}
