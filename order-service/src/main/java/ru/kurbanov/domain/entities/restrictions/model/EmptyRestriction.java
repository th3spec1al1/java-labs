package ru.kurbanov.domain.entities.restrictions.model;

import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;

public record EmptyRestriction() implements CarRestriction {

    @Override
    public boolean fits(Car car) {
        return true;
    }
}
