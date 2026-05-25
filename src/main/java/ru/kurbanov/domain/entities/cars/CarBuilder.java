package ru.kurbanov.domain.entities.cars;

import lombok.Builder;
import lombok.Getter;
import ru.kurbanov.domain.entities.cars.enums.BodyType;
import ru.kurbanov.domain.entities.cars.enums.CarDrive;
import ru.kurbanov.domain.entities.cars.enums.GearboxType;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.engines.Engine;
import ru.kurbanov.domain.exceptions.DomainValidationException;
import ru.kurbanov.domain.exceptions.IncompatibleComponentException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
public class CarBuilder {

    private final String brand;
    private final String model;
    private final Engine engine;
    private final BodyType body;
    private final CarDrive carDrive;
    private final GearboxType gearboxType;
    private final Map<String, Detail> details = new HashMap<>();
    private final String color;
    private final BigDecimal basePrice;

    @Builder
    public CarBuilder(String brand, String model, Engine engine, BodyType body, CarDrive carDrive,
                      GearboxType gearboxType, String color, BigDecimal basePrice) {
        this.brand = brand;
        this.model = model;
        this.engine = engine;
        this.body = body;
        this.carDrive = carDrive;
        this.gearboxType = gearboxType;
        this.color = color;
        this.basePrice = basePrice;
    }

    public CarBuilder withSelectedDetail(Detail detail) {
        if (!detail.getCompatibleModels().contains(this.brand + " " + this.model)) {
            throw new IncompatibleComponentException("You can't suit this detail - " + detail.getName()
                    + " with this car - " + brand + " " + model);
        }
        this.details.put(detail.getType(), detail);
        return this;
    }

    public Car build() {
        this.checkDetails();
        return new Car(brand, model, engine, body, carDrive, gearboxType, details, color, basePrice);
    }

    private void checkDetails() {
        String[] necessaryDetails = {"INTERIOR", "STEERING_WHEEL", "TRANSMISSION", "WHEELS"};

        for (String detail : necessaryDetails) {
            if (!details.containsKey(detail)) {
                throw new DomainValidationException("You can't build car without necessary detail - " + detail);
            }
        }
    }
}
