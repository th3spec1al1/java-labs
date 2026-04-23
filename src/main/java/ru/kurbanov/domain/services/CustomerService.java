package ru.kurbanov.domain.services;

import lombok.AllArgsConstructor;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.cars.CarFilter;
import ru.kurbanov.domain.entities.testdrives.TestDrive;
import ru.kurbanov.domain.entities.orders.available.AvailableOrder;
import ru.kurbanov.domain.entities.orders.custom.CustomOrder;
import ru.kurbanov.repositories.CarRepository;
import ru.kurbanov.repositories.AvailableOrderRepository;
import ru.kurbanov.repositories.CustomOrderRepository;
import ru.kurbanov.repositories.TestDriveRepository;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
public class CustomerService {

    private final CarRepository carRepository;
    private final AvailableOrderRepository availableOrderRepository;
    private final CustomOrderRepository customOrderRepository;
    private final TestDriveRepository testDriveRepository;

    public Car showCar(UUID id) {
        return carRepository.findById(id);
    }

    public Collection<Car> allCars() {
        return carRepository.show();
    }

    public Collection<Car> searchByFilters(CarFilter carFilter) {
        Collection<Car> res = this.allCars();
        res = carFilter.apply(res);
        return res;
    }

    public void addAvailableOrder(AvailableOrder availableOrder) {
        availableOrderRepository.save(availableOrder);
    }

    public void addCustomOrder(CustomOrder customOrder) {
        customOrderRepository.save(customOrder);
    }

    public void addTestDrive(TestDrive testDrive) {
        testDriveRepository.save(testDrive);
    }
}
