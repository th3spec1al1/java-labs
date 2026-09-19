package ru.kurbanov.domain.entities.restrictions.model;

import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;
import ru.kurbanov.domain.vo.Displacement;

public record DisplacementRestriction(Displacement displacement) implements CarRestriction {

    @Override
    public boolean fits(Car car) {
        return car.getEngine().displacement().equals(displacement);
    }
}
