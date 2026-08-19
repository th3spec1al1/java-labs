package ru.kurbanov.domain.entities.restrictions.model;

import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;
import ru.kurbanov.domain.entities.cars.enums.FuelType;

public record FuelRestriction(FuelType fuelType) implements CarRestriction {

    @Override
    public boolean fits(Car car) {
        return car.getEngine().fuelType().equals(fuelType);
    }
}
