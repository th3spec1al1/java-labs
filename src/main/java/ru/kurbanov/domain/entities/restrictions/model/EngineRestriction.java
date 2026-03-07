package ru.kurbanov.domain.entities.restrictions.model;

import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.engines.Engine;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;

public record EngineRestriction(Engine engine) implements CarRestriction {

    @Override
    public boolean fits(Car car) {
        return car.getEngine().equals(engine);
    }
}
