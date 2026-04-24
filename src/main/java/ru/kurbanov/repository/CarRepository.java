package ru.kurbanov.repository;

import ru.kurbanov.domain.entities.cars.Car;

import java.util.Collection;
import java.util.UUID;

public interface CarRepository {

    Car save(Car car);
    void delete(Car car);
    Car findById(UUID id);
    Collection<Car> show();
}
