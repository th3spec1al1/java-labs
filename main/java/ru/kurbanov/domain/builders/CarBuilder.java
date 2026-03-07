package ru.kurbanov.domain.builders;

import ru.kurbanov.domain.entities.bodies.Body;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.engines.Engine;
import ru.kurbanov.domain.vo.Money;

import java.util.Map;

public interface CarBuilder {

    CarBuilder withBaseStats(String brand, String model, Engine engine, Body body, String color, Money basePrice);
    CarBuilder withSelectedDetail(Detail detail);
    Car build();
}
