package ru.kurbanov.domain.factories.details.model;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.SteeringWheel;
import ru.kurbanov.domain.factories.details.DetailFactory;

import java.math.BigDecimal;
import java.util.List;

public class SteeringWheelFactory implements DetailFactory {

    @Override
    public Detail create(String name, String brand, BigDecimal price, List<String> models) {
        return new SteeringWheel(name, brand, price, models);
    }
}
