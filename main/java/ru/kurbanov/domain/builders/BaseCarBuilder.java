package ru.kurbanov.domain.builders;

import ru.kurbanov.domain.entities.bodies.Body;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.engines.Engine;
import ru.kurbanov.domain.exceptions.NotFoundException;
import ru.kurbanov.domain.exceptions.UnsuitableDetailException;
import ru.kurbanov.domain.vo.Money;

import java.util.HashMap;
import java.util.Map;

public class BaseCarBuilder implements CarBuilder {

    private String brand;
    private String model;

    private Engine engine;
    private Body body;
    private Map<String, Detail> details = new HashMap<>();
    private String color;

    private Money basePrice;

    @Override
    public CarBuilder withBaseStats(String brand, String model, Engine engine, Body body,
                                    String color, Money basePrice){
        this.brand = brand;
        this.model = model;
        this.engine = engine;
        this.body = body;
        this.color = color;
        this.basePrice = basePrice;

        return this;
    }

    @Override
    public CarBuilder withSelectedDetail(Detail detail) {
        if (!detail.getModels().contains(this.model)) {
            throw new UnsuitableDetailException("You can't suit this detail - " + detail.getName()
                    + " with this car - " + brand + " " + model);
        }
        this.details.put(detail.getType(), detail);
        return this;
    }

    @Override
    public Car build() {
        this.checkDetails();
        return new Car(brand, model, engine, body, details, color, basePrice);
    }

    private void checkDetails() {
        String[] necessaryDetails = {"Interior", "SteeringWheel", "Transmission", "Wheels"};

        for (String detail: necessaryDetails) {
            if (!details.containsKey(detail)) {
                throw new NotFoundException("You can't build car without necessary detail - " + detail);
            }
        }
    }
}
