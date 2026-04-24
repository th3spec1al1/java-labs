package ru.kurbanov.persistence.inmemory;

import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.repository.CarRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryCarRepository implements CarRepository {

    private final Map<UUID, Car> cars = new HashMap<>();

    @Override
    public Car save(Car car) {
        return cars.put(car.getId(), car);
    }

    @Override
    public void delete(Car car) {
        cars.remove(car.getId());
    }

    @Override
    public Car findById(UUID id) {
        return cars.get(id);
    }

    @Override
    public Collection<Car> show() {
        return cars.values();
    }
}
