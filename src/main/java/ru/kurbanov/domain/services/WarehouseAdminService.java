package ru.kurbanov.domain.services;

import lombok.AllArgsConstructor;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.repositories.CarRepository;
import ru.kurbanov.repositories.DetailRepository;
import ru.kurbanov.repositories.OrderRepository;
import ru.kurbanov.repositories.TestDriveRepository;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
public class WarehouseAdminService {

    private final CarRepository carRepository;
    private final DetailRepository detailRepository;
    private final OrderRepository orderRepository;
    private final TestDriveRepository testDriveRepository;

    public Car addCar(Car car) {
        carRepository.save(car);
        return car;
    }

    public Detail addDetail(Detail detail) {
        detailRepository.save(detail);
        return detail;
    }

    public Car showCar(UUID id) {
        return carRepository.findById(id);
    }

    public Detail showDetail(UUID id) {
        return detailRepository.findById(id);
    }

    public Car updateCar(Car car) {
        carRepository.save(car);
        return car;
    }

    public Detail updateDetail(Detail detail) {
        detailRepository.save(detail);
        return detail;
    }

    public Collection<Car> allCars() {
        return carRepository.show();
    }

    public Collection<Detail> allDetails() {
        return detailRepository.show();
    }
}
