package ru.kurbanov.domain.services;

import lombok.AllArgsConstructor;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.cars.CarFilter;
import ru.kurbanov.domain.entities.cars.TestDrive;
import ru.kurbanov.domain.entities.orders.Order;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;
import ru.kurbanov.repositories.CarRepository;
import ru.kurbanov.repositories.OrderRepository;
import ru.kurbanov.repositories.TestDriveRepository;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
public class CarDealershipManagerService {

    private final CarRepository carRepository;
    private final OrderRepository orderRepository;
    private final TestDriveRepository testDriveRepository;

    public Collection<Car> allCars() {
        return carRepository.show();
    }

    public Car showCar(UUID id) {
        return carRepository.findById(id);
    }

    public Collection<Order> allOrders() {
        return orderRepository.show();
    }

    public Collection<TestDrive> allTestDrives() {
        return testDriveRepository.show();
    }

    public void addTestDrive(TestDrive testDrive) {
        testDriveRepository.save(testDrive);
    }

    public void deleteTestDrive(TestDrive testDrive) {
        testDriveRepository.delete(testDriveRepository.findById(testDrive.getId()));
    }

    public Collection<Car> searchByFilters(CarFilter carFilter, CarRestriction... carRestrictions) {
        Collection<Car> res = this.allCars();
        res = CarFilter.applyAll(res, carRestrictions);

        return res;
    }
}
