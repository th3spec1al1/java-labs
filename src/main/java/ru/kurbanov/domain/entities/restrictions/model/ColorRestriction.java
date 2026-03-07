package ru.kurbanov.domain.entities.restrictions.model;

import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;

public record ColorRestriction(String color) implements CarRestriction {

    @Override
    public boolean fits(Car car) {
        return car.getColor().equals(color);
    }
}
