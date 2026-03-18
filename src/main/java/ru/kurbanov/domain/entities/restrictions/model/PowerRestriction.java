package ru.kurbanov.domain.entities.restrictions.model;

import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;
import ru.kurbanov.domain.vo.Power;

public record PowerRestriction(Power power) implements CarRestriction {

    @Override
    public boolean fits(Car car) {
        return car.getEngine().power().equals(power);
    }
}
