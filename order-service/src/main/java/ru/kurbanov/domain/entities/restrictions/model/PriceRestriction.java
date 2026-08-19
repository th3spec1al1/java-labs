package ru.kurbanov.domain.entities.restrictions.model;

import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;

import java.math.BigDecimal;

public record PriceRestriction(BigDecimal price) implements CarRestriction {

    @Override
    public boolean fits(Car car) {
        return (price.compareTo(car.getBasePrice()) >= 0);
    }
}
