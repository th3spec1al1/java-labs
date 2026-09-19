package ru.kurbanov.domain.entities.restrictions.model;

import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.cars.enums.BodyType;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;

public record BodyRestriction(BodyType body) implements CarRestriction {

    @Override
    public boolean fits(Car car) {
        return car.getBody().equals(body);
    }
}
