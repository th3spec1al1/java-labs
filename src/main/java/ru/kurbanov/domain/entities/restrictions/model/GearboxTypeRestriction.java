package ru.kurbanov.domain.entities.restrictions.model;

import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.cars.enums.GearboxType;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;

public record GearboxTypeRestriction(GearboxType gearbox) implements CarRestriction {

    @Override
    public boolean fits(Car car) {
        return car.getGearbox().equals(gearbox);
    }
}
