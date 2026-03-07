package ru.kurbanov.domain.factories.details.model;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.Wheels;
import ru.kurbanov.domain.factories.details.DetailFactory;

import java.math.BigDecimal;
import java.util.List;

public class WheelsFactory implements DetailFactory {

    @Override
    public Detail create(String name, String brand, BigDecimal price, List<String> models) {
        return new Wheels(name, brand, price, models);
    }
}
