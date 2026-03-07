package ru.kurbanov;

import org.junit.jupiter.api.Test;
import ru.kurbanov.domain.builders.CarBuilder;
import ru.kurbanov.domain.entities.bodies.model.Sedan;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.cars.CarFilter;
import ru.kurbanov.domain.entities.engines.Engine;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;
import ru.kurbanov.domain.entities.restrictions.model.BrandRestriction;
import ru.kurbanov.domain.entities.restrictions.model.ModelRestriction;
import ru.kurbanov.domain.entities.restrictions.model.UnitedRestrictions;
import ru.kurbanov.domain.enums.FuelType;
import ru.kurbanov.domain.factories.details.model.InteriorFactory;
import ru.kurbanov.domain.factories.details.model.SteeringWheelFactory;
import ru.kurbanov.domain.factories.details.model.TransmissionFactory;
import ru.kurbanov.domain.factories.details.model.WheelsFactory;
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
    public void FilterCarsShouldFiltered() {
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

        CustomerService customerService = new CustomerService(carRepository, orderRepository, testDriveRepository);

        // Act
        CarFilter brandFilter = new CarFilter().withBrand("BMW");
        CarFilter brandModelFilter = new CarFilter().withBrand("BMW").withModel("330i").withColor("Black");
        CarRestriction brandSpecification = new BrandRestriction("BMW");
        CarRestriction brandModelSpecifications = new UnitedRestrictions(brandSpecification, new ModelRestriction("330i"));
        Collection<Car> filteredCars = customerService.searchByFilters(brandFilter, brandSpecification);
        Collection<Car> filteredCarsByModel = customerService.searchByFilters(brandModelFilter, brandModelSpecifications);

        // Assert
        assertEquals(1, filteredCars.size());
        assertEquals(0, filteredCarsByModel.size());

    }
}
