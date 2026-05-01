package ru.kurbanov.infrastructure.persistence.jpa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kurbanov.domain.entities.cars.enums.CarDrive;
import ru.kurbanov.domain.entities.cars.enums.FuelType;
import ru.kurbanov.domain.entities.cars.enums.GearboxType;
import ru.kurbanov.infrastructure.persistence.jpa.listeners.BaseEntityListener;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cars")
public class CarEntity extends BaseEntity {

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "engine_power", nullable = false)
    private int enginePower;

    @Column(name = "engine_displacement", nullable = false)
    private int engineDisplacement;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;

    @Column(name = "car_body", nullable = false)
    private String carBody;

    @Enumerated(EnumType.STRING)
    @Column(name = "car_drive", nullable = false)
    private CarDrive carDrive;

    @Enumerated(EnumType.STRING)
    @Column(name = "gearbox_type", nullable = false)
    private GearboxType gearboxType;

    @Column(name = "wheels_id", nullable = false)
    private UUID wheelsId;

    @Column(name = "transmission_id", nullable = false)
    private UUID transmissionId;

    @Column(name = "steering_wheel_id", nullable = false)
    private UUID steeringWheelId;

    @Column(name = "interior_id", nullable = false)
    private UUID interiorId;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}
