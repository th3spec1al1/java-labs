package ru.kurbanov.domain.entities.cars;

import lombok.Getter;
import lombok.Setter;
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
@Setter
public class CarBuilder {

    private String brand;
    private String model;
    private Engine engine;
    private BodyType body;
    private CarDrive carDrive;
    private GearboxType gearboxType;
    private final Map<String, Detail> details = new HashMap<>();
    private String color;
    private BigDecimal basePrice;

    public static CarBuilder create() {
        return new CarBuilder();
    }

    public CarBuilder brand(String brand) { this.brand = brand; return this; }
    public CarBuilder model(String model) { this.model = model; return this; }
    public CarBuilder engine(Engine engine) { this.engine = engine; return this; }
    public CarBuilder body(BodyType body) { this.body = body; return this; }
    public CarBuilder carDrive(CarDrive carDrive) { this.carDrive = carDrive; return this; }
    public CarBuilder gearboxType(GearboxType gearboxType) { this.gearboxType = gearboxType; return this; }
    public CarBuilder color(String color) { this.color = color; return this; }
    public CarBuilder basePrice(BigDecimal basePrice) { this.basePrice = basePrice; return this; }

    public CarBuilder withSelectedDetail(Detail detail) {
        if (detail == null) return this;
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
