package ru.kurbanov.domain.entities.restrictions.model;

import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.cars.enums.CarDrive;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;

public record CarDriveRestriction(CarDrive carDrive) implements CarRestriction {

    @Override
    public boolean fits(Car car) {
        return car.getCarDrive().equals(carDrive);
    }
}
