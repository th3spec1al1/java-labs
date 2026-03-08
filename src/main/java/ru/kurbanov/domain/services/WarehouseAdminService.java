package ru.kurbanov.domain.services;

import lombok.AllArgsConstructor;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.repositories.*;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
public class WarehouseAdminService {

    private final CarRepository carRepository;
    private final DetailRepository detailRepository;

    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    public Detail addDetail(Detail detail) {
        return detailRepository.save(detail);
    }

    public Collection<Car> allCars() {
        return carRepository.show();
    }

    public Collection<Detail> allDetails() {
        return detailRepository.show();
    }

    public Car showCar(UUID id) {
        return carRepository.findById(id);
    }

    public Detail showDetail(UUID id) {
        return detailRepository.findById(id);
    }

    public Car updateCar(Car car) {
        return carRepository.save(car);
    }

    public Detail updateDetail(Detail detail) {
        return detailRepository.save(detail);
    }
}
