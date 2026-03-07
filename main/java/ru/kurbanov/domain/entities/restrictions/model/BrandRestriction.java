package ru.kurbanov.domain.entities.restrictions.model;

import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;

public record BrandRestriction(String brand) implements CarRestriction {

    @Override
    public boolean fits(Car car) {
        return car.getBrand().equals(brand);
    }
}
