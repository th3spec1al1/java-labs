package ru.kurbanov.domain.entities.restrictions;

import ru.kurbanov.domain.entities.cars.Car;

public interface CarRestriction {

    boolean fits(Car car);
}
