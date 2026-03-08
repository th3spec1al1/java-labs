package ru.kurbanov.domain.entities.restrictions.model;

import ru.kurbanov.domain.entities.bodies.Body;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;

public record BodyRestriction(Body body) implements CarRestriction {

    @Override
    public boolean fits(Car car) {
        return car.getBody().getType().equals(body.getType());
    }
}
