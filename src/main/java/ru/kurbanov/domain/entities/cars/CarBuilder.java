package ru.kurbanov.domain.entities.cars;

import lombok.Builder;
import lombok.Getter;
import ru.kurbanov.domain.entities.bodies.Body;
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
    private final Body body;
    private final Map<String, Detail> details = new HashMap<>();
    private final String color;
    private final BigDecimal basePrice;

    @Builder
    public CarBuilder(String brand, String model, Engine engine, Body body,
                      String color, BigDecimal basePrice) {
        this.brand = brand;
        this.model = model;
        this.engine = engine;
        this.body = body;
        this.color = color;
        this.basePrice = basePrice;
    }

    public CarBuilder withSelectedDetail(Detail detail) {
        if (!detail.getModels().contains(this.model)) {
            throw new IncompatibleComponentException("You can't suit this detail - " + detail.getName()
                    + " with this car - " + brand + " " + model);
        }
        this.details.put(detail.getType(), detail);
        return this;
    }

    public Car build() {
        this.checkDetails();
        return new Car(brand, model, engine, body, details, color, basePrice);
    }

    private void checkDetails() {
        String[] necessaryDetails = {"Interior", "SteeringWheel", "Transmission", "Wheels"};

        for (String detail : necessaryDetails) {
            if (!details.containsKey(detail)) {
                throw new DomainValidationException("You can't build car without necessary detail - " + detail);
            }
        }
    }
}
