package ru.kurbanov.domain.entities.cars;

import lombok.Getter;
import ru.kurbanov.domain.entities.cars.enums.BodyType;
import ru.kurbanov.domain.entities.cars.enums.CarDrive;
import ru.kurbanov.domain.entities.cars.enums.GearboxType;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.engines.Engine;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Getter
public class Car {

    private final UUID id;
    private final String brand;
    private final String model;

    private final Engine engine;
    private final BodyType body;
    private final CarDrive carDrive;
    private final GearboxType gearboxType;
    private final Map<String, Detail> details;
    private final String color;

    private final BigDecimal basePrice;

    public Car(String brand, String model, Engine engine, BodyType body, CarDrive carDrive,
               GearboxType gearboxType, Map<String, Detail> details, String color, BigDecimal basePrice) {
        this.id = UUID.randomUUID();
        this.brand = brand;
        this.model = model;
        this.engine = engine;
        this.body = body;
        this.carDrive = carDrive;
        this.gearboxType = gearboxType;
        this.details = details;
        this.color = color;
        this.basePrice = basePrice;
    }

    public Car(UUID id, String brand, String model, Engine engine, BodyType body, CarDrive carDrive,
               GearboxType gearboxType, Map<String, Detail> details, String color, BigDecimal basePrice) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.engine = engine;
        this.body = body;
        this.carDrive = carDrive;
        this.gearboxType = gearboxType;
        this.details = details;
        this.color = color;
        this.basePrice = basePrice;
    }

    public BigDecimal getFinalPrice() {
        BigDecimal finalPrice = basePrice;

        for (Detail detail: details.values()) {
            finalPrice = finalPrice.add(detail.getPrice());
        }
        return finalPrice;
    }
}
