package ru.kurbanov.domain.services;

import lombok.AllArgsConstructor;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.testdrives.TestDrive;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.orders.available.AvailableOrder;
import ru.kurbanov.domain.entities.orders.custom.CustomOrder;
import ru.kurbanov.repositories.*;

import java.util.UUID;

@AllArgsConstructor
public class SystemAdminService {

    private final CarRepository carRepository;
    private final DetailRepository detailRepository;
    private final AvailableOrderRepository availableOrderRepository;
    private final CustomOrderRepository customOrderRepository;
    private final TestDriveRepository testDriveRepository;

    public Car showCar(UUID id) {
        return carRepository.findById(id);
    }

    public Detail showDetail(UUID id) {
        return detailRepository.findById(id);
    }

    public AvailableOrder showAvailableOrder(UUID id) {
        return availableOrderRepository.findById(id);
    }

    public CustomOrder showCustomOrder(UUID id) {
        return customOrderRepository.findById(id);
    }

    public TestDrive showTestDrive(UUID id) {
        return testDriveRepository.findById(id);
    }

    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    public Detail addDetail(Detail detail) {
        return detailRepository.save(detail);
    }

    public AvailableOrder addAvailableOrder(AvailableOrder availableOrder) {
        return availableOrderRepository.save(availableOrder);
    }

    public CustomOrder addCustomOrder(CustomOrder customOrder) {
        return customOrderRepository.save(customOrder);
    }

    public TestDrive addTestDrive(TestDrive testDrive) {
        return testDriveRepository.save(testDrive);
    }

    public Car updateCar(Car car) {
        return carRepository.save(car);
    }

    public Detail updateDetail(Detail detail) {
        return detailRepository.save(detail);
    }

    public AvailableOrder updateAvailableOrder(AvailableOrder availableOrder) {
        return availableOrderRepository.save(availableOrder);
    }

    public CustomOrder updateCustomOrder(CustomOrder customOrder) {
        return customOrderRepository.save(customOrder);
    }

    public TestDrive updateTestDrive(TestDrive testDrive) {
        return testDriveRepository.save(testDrive);
    }

    public void deleteCar(Car car) {
        carRepository.delete(carRepository.findById(car.getId()));
    }

    public void deleteDetail(Detail detail) {
        detailRepository.delete(detailRepository.findById(detail.getId()));
    }

    public void deleteAvailableOrder(AvailableOrder availableOrder) {
        availableOrderRepository.delete(availableOrderRepository.findById(availableOrder.getId()));
    }

    public void deleteCustomOrder(CustomOrder customOrder) {
        customOrderRepository.delete(customOrderRepository.findById(customOrder.getId()));
    }

    public void deleteTestDrive(TestDrive testDrive) {
        testDriveRepository.delete(testDriveRepository.findById(testDrive.getId()));
    }
}
