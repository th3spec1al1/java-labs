package ru.kurbanov;

import org.junit.jupiter.api.Test;
import ru.kurbanov.domain.builders.CarBuilder;
import ru.kurbanov.domain.entities.bodies.Body;
import ru.kurbanov.domain.entities.bodies.model.Sedan;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.cars.CarFilter;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.engines.Engine;
import ru.kurbanov.domain.enums.FuelType;
import ru.kurbanov.domain.factories.details.model.InteriorFactory;
import ru.kurbanov.domain.factories.details.model.SteeringWheelFactory;
import ru.kurbanov.domain.factories.details.model.TransmissionFactory;
import ru.kurbanov.domain.factories.details.model.WheelsFactory;
import ru.kurbanov.domain.services.CustomerService;
import ru.kurbanov.domain.vo.Displacement;
import ru.kurbanov.domain.vo.Power;
import ru.kurbanov.repositories.AvailableOrderRepository;
import ru.kurbanov.repositories.CarRepository;
import ru.kurbanov.repositories.CustomOrderRepository;
import ru.kurbanov.repositories.TestDriveRepository;
import ru.kurbanov.repositories.inmemory.InMemoryAvailableOrderRepository;
import ru.kurbanov.repositories.inmemory.InMemoryCarRepository;
import ru.kurbanov.repositories.inmemory.InMemoryCustomOrderRepository;
import ru.kurbanov.repositories.inmemory.InMemoryTestDriveRepository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterTests {

    @Test
    public void OneFilterCarsShouldFiltered() {

        // Arrange
        CarRepository carRepository = new InMemoryCarRepository();
        AvailableOrderRepository availableOrderRepository = new InMemoryAvailableOrderRepository();
        CustomOrderRepository customOrderRepository = new InMemoryCustomOrderRepository();
        TestDriveRepository testDriveRepository = new InMemoryTestDriveRepository();

        Engine engine = new Engine(new Power(200), new Displacement(4), FuelType.PETROL);
        Body body = new Sedan();
        BigDecimal price = new BigDecimal(4_500_000);

        Detail transmission = new TransmissionFactory().create("8AT", "BMW",
                BigDecimal.ZERO, List.of("320i", "330i"));
        Detail steeringWheel = new SteeringWheelFactory().create("Спортивный кожаный", "BMW",
                BigDecimal.ZERO, List.of("320i", "330i"));
        Detail wheels = new WheelsFactory().create("17’’ Standard", "BMW",
                BigDecimal.ZERO, List.of("320i"));
        Detail interior = new InteriorFactory().create("Dakota", "BMW",
                new BigDecimal(110_000), List.of("320i", "330i"));

        CarBuilder carBuilder1 = new CarBuilder("BMW", "320i", engine, body, "White", price);
        CarBuilder carBuilder2 = new CarBuilder("BMW", "320i", engine, body, "Black", price);

        Car car1 = carBuilder1
                .withSelectedDetail(transmission)
                .withSelectedDetail(steeringWheel)
                .withSelectedDetail(wheels)
                .withSelectedDetail(interior)
                .build();
        Car car2 = carBuilder2
                .withSelectedDetail(transmission)
                .withSelectedDetail(steeringWheel)
                .withSelectedDetail(wheels)
                .withSelectedDetail(interior)
                .build();

        carRepository.save(car1);
        carRepository.save(car2);

        CustomerService customerService = new CustomerService(carRepository, availableOrderRepository,
                customOrderRepository, testDriveRepository);

        // Act
        CarFilter carFilter1 = new CarFilter().withBody(new Sedan());
        CarFilter carFilter2 = new CarFilter().withColor("Black");
        Collection<Car> filteredCars1 = customerService.searchByFilters(carFilter1);
        Collection<Car> filteredCars2 = customerService.searchByFilters(carFilter2);

        // Assert
        assertEquals(2, filteredCars1.size());
        assertEquals(1, filteredCars2.size());
    }
}
