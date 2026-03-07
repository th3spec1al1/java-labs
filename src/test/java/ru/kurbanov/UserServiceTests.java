package ru.kurbanov;

import org.junit.jupiter.api.Test;
import ru.kurbanov.domain.builders.CarBuilder;
import ru.kurbanov.domain.entities.bodies.model.Coupe;
import ru.kurbanov.domain.entities.bodies.model.Sedan;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.cars.CarFilter;
import ru.kurbanov.domain.entities.cars.TestDrive;
import ru.kurbanov.domain.entities.engines.Engine;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;
import ru.kurbanov.domain.entities.restrictions.model.*;
import ru.kurbanov.domain.entities.users.Customer;
import ru.kurbanov.domain.enums.FuelType;
import ru.kurbanov.domain.factories.details.model.InteriorFactory;
import ru.kurbanov.domain.factories.details.model.SteeringWheelFactory;
import ru.kurbanov.domain.factories.details.model.TransmissionFactory;
import ru.kurbanov.domain.factories.details.model.WheelsFactory;
import ru.kurbanov.domain.services.CarDealershipManagerService;
import ru.kurbanov.domain.services.CustomerService;
import ru.kurbanov.domain.vo.Displacement;
import ru.kurbanov.domain.vo.Power;
import ru.kurbanov.repositories.CarRepository;
import ru.kurbanov.repositories.OrderRepository;
import ru.kurbanov.repositories.TestDriveRepository;
import ru.kurbanov.repositories.inmemory.InMemoryCarRepository;
import ru.kurbanov.repositories.inmemory.InMemoryOrderRepository;
import ru.kurbanov.repositories.inmemory.InMemoryTestDriveRepository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTests {

    @Test
    public void SomeFiltersCarsShouldFiltered() {
        // Arrange
        CarRepository carRepository = new InMemoryCarRepository();
        OrderRepository orderRepository = new InMemoryOrderRepository();
        TestDriveRepository testDriveRepository = new InMemoryTestDriveRepository();

        carRepository.save(new CarBuilder("BMW", "320i",
                        new Engine(new Power(184), new Displacement(2), FuelType.PETROL),
                        new Sedan(), "Black", new BigDecimal(4_500_000))
                .withSelectedDetail(new TransmissionFactory().create("8AT", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i")))
                .withSelectedDetail(new SteeringWheelFactory().create("Спортивный кожаный", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i")))
                .withSelectedDetail(new WheelsFactory().create("17’’ Standard", "BMW",
                        BigDecimal.ZERO, List.of("320i")))
                .withSelectedDetail(new InteriorFactory().create("Dakota", "BMW",
                        new BigDecimal(110_000), List.of("320i", "330i")))
                .build()
        );
        carRepository.save(new CarBuilder("BMW", "330i",
                    new Engine(new Power(190), new Displacement(2), FuelType.PETROL),
                    new Sedan(), "White", new BigDecimal(4_500_000))
                .withSelectedDetail(new TransmissionFactory().create("8AT", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i")))
                .withSelectedDetail(new SteeringWheelFactory().create("Спортивный кожаный", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i")))
                .withSelectedDetail(new WheelsFactory().create("18’’ Aero", "BMW",
                        BigDecimal.ZERO, List.of("330i")))
                .withSelectedDetail(new InteriorFactory().create("Dakota", "BMW",
                        new BigDecimal(110_000), List.of("320i", "330i")))
                .build()
        );

        CustomerService customerService = new CustomerService(carRepository, orderRepository, testDriveRepository);

        // Act
        CarFilter brandFilter = new CarFilter().withBrand("BMW");
        CarFilter brandModelFilter = new CarFilter().withBrand("BMW").withModel("320i");
        CarRestriction brandSpecification = new BrandRestriction("BMW");
        CarRestriction brandModelSpecifications = new UnitedRestrictions(brandSpecification, new ModelRestriction("330i"));
        Collection<Car> filteredCars = customerService.searchByFilters(brandFilter, brandSpecification);
        Collection<Car> filteredCarsByModel = customerService.searchByFilters(brandModelFilter, brandModelSpecifications);

        // Assert
        assertEquals(2, filteredCars.size());
        assertEquals(1, filteredCarsByModel.size());
    }

    @Test
    public void ShouldFilterCarsByPriceRange() {
        // Arrange
        CarRepository carRepository = new InMemoryCarRepository();
        OrderRepository orderRepository = new InMemoryOrderRepository();
        TestDriveRepository testDriveRepository = new InMemoryTestDriveRepository();

        carRepository.save(new CarBuilder("BMW", "320i",
                new Engine(new Power(184), new Displacement(2), FuelType.PETROL),
                new Sedan(), "Black", new BigDecimal(4_500_000))
                .withSelectedDetail(new TransmissionFactory().create("8AT", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i")))
                .withSelectedDetail(new SteeringWheelFactory().create("Спортивный кожаный", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i")))
                .withSelectedDetail(new WheelsFactory().create("18’’ Aero", "BMW",
                        BigDecimal.ZERO, List.of("320i")))
                .withSelectedDetail(new InteriorFactory().create("Dakota", "BMW",
                        new BigDecimal(110_000), List.of("320i", "330i")))
                .build()
        );
        carRepository.save(new CarBuilder("BMW", "330i",
                new Engine(new Power(245), new Displacement(2), FuelType.PETROL),
                new Sedan(), "White", new BigDecimal(5_500_000))
                .withSelectedDetail(new TransmissionFactory().create("8AT", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i")))
                .withSelectedDetail(new SteeringWheelFactory().create("Спортивный кожаный", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i")))
                .withSelectedDetail(new WheelsFactory().create("18’’ Aero", "BMW",
                        BigDecimal.ZERO, List.of("330i")))
                .withSelectedDetail(new InteriorFactory().create("Dakota", "BMW",
                        new BigDecimal(110_000), List.of("320i", "330i")))
                .build()
        );
        carRepository.save(new CarBuilder("Audi", "RS6",
                new Engine(new Power(600), new Displacement(4), FuelType.PETROL),
                new Coupe(), "Blue", new BigDecimal(12_000_000))
                .withSelectedDetail(new TransmissionFactory().create("8AT", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i", "RS6")))
                .withSelectedDetail(new SteeringWheelFactory().create("Спортивный кожаный", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i", "RS6")))
                .withSelectedDetail(new WheelsFactory().create("18’’ Aero", "BMW",
                        BigDecimal.ZERO, List.of("330i", "RS6")))
                .withSelectedDetail(new InteriorFactory().create("Dakota", "BMW",
                        new BigDecimal(110_000), List.of("320i", "330i", "RS6")))
                .build()
        );

        CustomerService customerService = new CustomerService(carRepository, orderRepository, testDriveRepository);

        // Act
        BigDecimal maxPrice = new BigDecimal(6_000_000);
        CarFilter priceFilter = new CarFilter().withPrice(maxPrice);
        CarRestriction priceRestriction = new PriceRestriction(maxPrice);

        Collection<Car> filteredCars = customerService.searchByFilters(priceFilter, priceRestriction);

        // Assert
        assertEquals(2, filteredCars.size());
    }

    @Test
    public void ShouldFilterCarsByBodyType() {
        // Arrange
        CarRepository carRepository = new InMemoryCarRepository();
        OrderRepository orderRepository = new InMemoryOrderRepository();
        TestDriveRepository testDriveRepository = new InMemoryTestDriveRepository();

        carRepository.save(new CarBuilder("BMW", "320i",
                new Engine(new Power(184), new Displacement(2), FuelType.PETROL),
                new Sedan(), "Black", new BigDecimal(4_500_000))
                .withSelectedDetail(new TransmissionFactory().create("8AT", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i")))
                .withSelectedDetail(new SteeringWheelFactory().create("Спортивный кожаный", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i")))
                .withSelectedDetail(new WheelsFactory().create("18’’ Aero", "BMW",
                        BigDecimal.ZERO, List.of("320i")))
                .withSelectedDetail(new InteriorFactory().create("Dakota", "BMW",
                        new BigDecimal(110_000), List.of("320i", "330i")))
                .build()
        );
        carRepository.save(new CarBuilder("BMW", "X5",
                new Engine(new Power(340), new Displacement(3), FuelType.PETROL),
                new Coupe(), "Black", new BigDecimal(7_500_000))
                .withSelectedDetail(new TransmissionFactory().create("8AT", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i", "X5")))
                .withSelectedDetail(new SteeringWheelFactory().create("Спортивный кожаный", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i", "X5")))
                .withSelectedDetail(new WheelsFactory().create("18’’ Aero", "BMW",
                        BigDecimal.ZERO, List.of("X5")))
                .withSelectedDetail(new InteriorFactory().create("Dakota", "BMW",
                        new BigDecimal(110_000), List.of("320i", "330i", "X5")))
                .build()
        );
        carRepository.save(new CarBuilder("Audi", "A4",
                new Engine(new Power(190), new Displacement(2), FuelType.PETROL),
                new Sedan(), "Silver", new BigDecimal(4_300_000))
                .withSelectedDetail(new TransmissionFactory().create("8AT", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i", "A4")))
                .withSelectedDetail(new SteeringWheelFactory().create("Спортивный кожаный", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i", "A4")))
                .withSelectedDetail(new WheelsFactory().create("18’’ Aero", "BMW",
                        BigDecimal.ZERO, List.of("A4")))
                .withSelectedDetail(new InteriorFactory().create("Dakota", "BMW",
                        new BigDecimal(110_000), List.of("320i", "330i", "A4")))
                .build()
        );
        carRepository.save(new CarBuilder("Mercedes", "E200",
                new Engine(new Power(197), new Displacement(2), FuelType.PETROL),
                new Sedan(), "Gray", new BigDecimal(5_000_000))
                .withSelectedDetail(new TransmissionFactory().create("8AT", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i", "E200")))
                .withSelectedDetail(new SteeringWheelFactory().create("Спортивный кожаный", "BMW",
                        BigDecimal.ZERO, List.of("320i", "330i", "E200")))
                .withSelectedDetail(new WheelsFactory().create("18’’ Aero", "BMW",
                        BigDecimal.ZERO, List.of("330i", "E200")))
                .withSelectedDetail(new InteriorFactory().create("Dakota", "BMW",
                        new BigDecimal(110_000), List.of("320i", "330i", "E200")))
                .build()
        );

        CustomerService customerService = new CustomerService(carRepository, orderRepository, testDriveRepository);

        // Act
        CarFilter sedanFilter = new CarFilter().withColor("Black");
        CarRestriction sedanRestriction = new ColorRestriction("Black");

        Collection<Car> filteredCars = customerService.searchByFilters(sedanFilter, sedanRestriction);

        // Assert
        assertEquals(2, filteredCars.size());
    }
}
