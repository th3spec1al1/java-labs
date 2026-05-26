package ru.kurbanov.application.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ru.kurbanov.application.abstractions.repositories.jpa.JpaCarRepository;
import ru.kurbanov.application.abstractions.repositories.jpa.JpaDetailRepository;
import ru.kurbanov.application.contracts.CarService;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.cars.CarBuilder;
import ru.kurbanov.domain.entities.cars.enums.BodyType;
import ru.kurbanov.domain.entities.cars.enums.CarDrive;
import ru.kurbanov.domain.entities.cars.enums.FuelType;
import ru.kurbanov.domain.entities.cars.enums.GearboxType;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.engines.Engine;
import ru.kurbanov.domain.vo.Displacement;
import ru.kurbanov.domain.vo.Power;
import ru.kurbanov.infrastructure.persistence.jpa.model.CarEntity;
import ru.kurbanov.infrastructure.persistence.mappers.CarEntityMapper;
import ru.kurbanov.infrastructure.persistence.mappers.DetailEntityMapper;
import ru.kurbanov.infrastructure.persistence.specifications.CarSpecifications;
import ru.kurbanov.presentation.dto.requests.CarConfigureRequestDto;
import ru.kurbanov.presentation.dto.requests.CarFilterRequestDto;
import ru.kurbanov.presentation.dto.requests.CarRequestDto;
import ru.kurbanov.presentation.dto.responses.CarResponseDto;
import ru.kurbanov.presentation.mappers.CarDtoMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CarServiceImpl implements CarService {

    private final JpaCarRepository carRepository;
    private final JpaDetailRepository detailRepository;
    private final CarEntityMapper carEntityMapper;
    private final DetailEntityMapper detailEntityMapper;
    private final CarDtoMapper carDtoMapper;
    private final CarDetailsLoader carDetailsLoader;

    private Engine createEngine(CarRequestDto carRequestDto) {
        return new Engine(
                new Power(carRequestDto.getEnginePower()),
                new Displacement(carRequestDto.getEngineDisplacement()),
                FuelType.valueOf(carRequestDto.getFuelType())
        );
    }

    @Override
    public CarResponseDto getCar(UUID carId) {
        CarEntity carEntity = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found: " + carId));

        Map<String, Detail> details = carDetailsLoader.loadDetails(carEntity);
        Car car = carEntityMapper.toDomain(carEntity, details);
        return carDtoMapper.toDto(car);
    }

    @Override
    public List<CarResponseDto> getCars(CarFilterRequestDto carFilterRequestDto) {
        BodyType bodyType = carFilterRequestDto.getCarBody() != null
                ? BodyType.valueOf(carFilterRequestDto.getCarBody()) : null;
        FuelType fuelType = carFilterRequestDto.getFuelType() != null
                ? FuelType.valueOf(carFilterRequestDto.getFuelType()) : null;
        CarDrive carDrive = carFilterRequestDto.getCarDrive() != null
                ? CarDrive.valueOf(carFilterRequestDto.getCarDrive()) : null;
        GearboxType gearboxType = carFilterRequestDto.getGearboxType() != null
                ? GearboxType.valueOf(carFilterRequestDto.getGearboxType()) : null;

        Specification<CarEntity> specification = CarSpecifications.buildFilter(
                carFilterRequestDto.getBrand(),
                carFilterRequestDto.getModel(),
                fuelType,
                bodyType,
                carDrive,
                gearboxType,
                carFilterRequestDto.getColor(),
                carFilterRequestDto.getMaxPrice(),
                carFilterRequestDto.getMinPower(),
                carFilterRequestDto.getMinDisplacement(),
                carFilterRequestDto.getInteriorId(),
                carFilterRequestDto.getSteeringWheelId(),
                carFilterRequestDto.getTransmissionId(),
                carFilterRequestDto.getWheelsId()
        );

        return carRepository.findAll(specification).stream()
                .map(entity -> {
                    Map<String, Detail> details = carDetailsLoader.loadDetails(entity);
                        Car car = carEntityMapper.toDomain(entity, details);
                        return carDtoMapper.toDto(car);
                })
                .toList();
    }

    @Override
    public CarResponseDto addCar(CarRequestDto carRequestDto) {
        Detail wheels = carDetailsLoader.loadDetail(carRequestDto.getWheelsId());
        Detail interior = carDetailsLoader.loadDetail(carRequestDto.getInteriorId());
        Detail transmission = carDetailsLoader.loadDetail(carRequestDto.getTransmissionId());
        Detail steeringWheel = carDetailsLoader.loadDetail(carRequestDto.getSteeringWheelId());

        Map<String, Detail> details = Map.of(
                "WHEELS", wheels,
                "INTERIOR", interior,
                "TRANSMISSION", transmission,
                "STEERING_WHEEL", steeringWheel
        );

        Car car = CarBuilder
                .create()
                .brand(carRequestDto.getBrand())
                .model(carRequestDto.getModel())
                .engine(createEngine(carRequestDto))
                .body(BodyType.valueOf(carRequestDto.getCarBody()))
                .color(carRequestDto.getColor())
                .basePrice(carRequestDto.getPrice())
                .withSelectedDetail(wheels)
                .withSelectedDetail(interior)
                .withSelectedDetail(transmission)
                .withSelectedDetail(steeringWheel)
                .build();

        CarEntity carEntity = carEntityMapper.toEntity(car);
        CarEntity saved = carRepository.save(carEntity);
        Car savedCar = carEntityMapper.toDomain(saved, details);
        return carDtoMapper.toDto(savedCar);
    }

    @Override
    public CarResponseDto updateCar(UUID carId, CarRequestDto carRequestDto) {
        CarEntity carEntity = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found: " + carId));

        carEntity.setBrand(carRequestDto.getBrand());
        carEntity.setModel(carRequestDto.getModel());
        carEntity.setColor(carRequestDto.getColor());
        carEntity.setEnginePower(carRequestDto.getEnginePower());
        carEntity.setEngineDisplacement(carRequestDto.getEngineDisplacement());
        carEntity.setFuelType(FuelType.valueOf(carRequestDto.getFuelType()));
        carEntity.setCarBody(BodyType.valueOf(carRequestDto.getCarBody()));
        carEntity.setCarDrive(CarDrive.valueOf(carRequestDto.getCarDrive()));
        carEntity.setGearboxType(GearboxType.valueOf(carRequestDto.getGearboxType()));
        carEntity.setWheelsId(carRequestDto.getWheelsId());
        carEntity.setTransmissionId(carRequestDto.getTransmissionId());
        carEntity.setSteeringWheelId(carRequestDto.getSteeringWheelId());
        carEntity.setInteriorId(carRequestDto.getInteriorId());
        carEntity.setPrice(carRequestDto.getPrice());

        Map<String, Detail> details = carDetailsLoader.loadDetails(carEntity);
        Car car = carEntityMapper.toDomain(carEntity, details);
        return carDtoMapper.toDto(car);
    }

    @Override
    public void removeCar(UUID carId) {
        carRepository.deleteById(carId);
    }

    @Override
    public CarResponseDto configureCar(UUID carId, CarConfigureRequestDto carConfigureRequestDto){
        CarEntity carEntity = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found: " + carId));

        Map<String, Detail> details = carDetailsLoader.loadDetails(carEntity);
        Car car = carEntityMapper.toDomain(carEntity, details);

        CarBuilder carBuilder = CarBuilder
                .create()
                .model(car.getModel())
                .brand(car.getBrand())
                .engine(car.getEngine())
                .body(car.getBody())
                .carDrive(car.getCarDrive())
                .gearboxType(car.getGearboxType())
                .color(car.getColor())
                .basePrice(car.getBasePrice());

        if (carConfigureRequestDto.getWheelsId() != null) {
            Detail detail = carDetailsLoader.loadDetail(carConfigureRequestDto.getWheelsId());
            details.put(detail.getType(), detail);
            carBuilder.withSelectedDetail(detail);
        } else {
            carBuilder.withSelectedDetail(details.get("WHEELS"));
        }

        if (carConfigureRequestDto.getWheelsId() != null) {
            Detail detail = carDetailsLoader.loadDetail(carConfigureRequestDto.getTransmissionId());
            details.put(detail.getType(), detail);
            carBuilder.withSelectedDetail(detail);
        } else {
            carBuilder.withSelectedDetail(details.get("TRANSMISSION"));
        }

        if (carConfigureRequestDto.getWheelsId() != null) {
            Detail detail = carDetailsLoader.loadDetail(carConfigureRequestDto.getSteeringWheelId());
            details.put(detail.getType(), detail);
            carBuilder.withSelectedDetail(detail);
        } else {
            carBuilder.withSelectedDetail(details.get("STEERING_WHEEL"));
        }

        if (carConfigureRequestDto.getWheelsId() != null) {
            Detail detail = carDetailsLoader.loadDetail(carConfigureRequestDto.getInteriorId());
            details.put(detail.getType(), detail);
            carBuilder.withSelectedDetail(detail);
        } else {
            carBuilder.withSelectedDetail(details.get("INTERIOR"));
        }

        Car newCar = carBuilder.build();
        CarEntity newCarEntity = carEntityMapper.toEntity(newCar);
        CarEntity saved = carRepository.save(newCarEntity);
        Car savedCar = carEntityMapper.toDomain(saved, details);
        return carDtoMapper.toDto(savedCar);
    }
}
