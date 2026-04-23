package ru.kurbanov;

import org.junit.jupiter.api.Test;
import ru.kurbanov.domain.entities.cars.CarBuilder;
import ru.kurbanov.domain.entities.bodies.Body;
import ru.kurbanov.domain.entities.bodies.model.Sedan;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.engines.Engine;
import ru.kurbanov.domain.enums.FuelType;
import ru.kurbanov.domain.exceptions.DomainValidationException;
import ru.kurbanov.domain.exceptions.IncompatibleComponentException;
import ru.kurbanov.domain.entities.details.factories.model.InteriorFactory;
import ru.kurbanov.domain.entities.details.factories.model.SteeringWheelFactory;
import ru.kurbanov.domain.entities.details.factories.model.TransmissionFactory;
import ru.kurbanov.domain.entities.details.factories.model.WheelsFactory;
import ru.kurbanov.domain.vo.Displacement;
import ru.kurbanov.domain.vo.Power;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarTests {

    @Test
    public void BuildCarShouldBuilt() {

        // Arrange
        Engine engine = new Engine(new Power(200), new Displacement(4), FuelType.PETROL);
        Body body = new Sedan();
        BigDecimal price = new BigDecimal(4_500_000);

        Detail transmission = new TransmissionFactory().create("8AT", "BMW",
                BigDecimal.ZERO, List.of("BMW 320i", "BMW 330i"));
        Detail steeringWheel = new SteeringWheelFactory().create("Спортивный кожаный", "BMW",
                BigDecimal.ZERO, List.of("BMW 320i", "BMW 330i"));
        Detail wheels = new WheelsFactory().create("17’’ Standard", "BMW",
                BigDecimal.ZERO, List.of("BMW 320i"));
        Detail interior = new InteriorFactory().create("Dakota", "BMW",
                new BigDecimal(110_000), List.of("BMW 320i", "BMW 330i"));

        CarBuilder carBuilder = new CarBuilder("BMW", "320i", engine, body, "White", price)
                .withSelectedDetail(transmission)
                .withSelectedDetail(steeringWheel)
                .withSelectedDetail(wheels)
                .withSelectedDetail(interior);

        // Act
        Car car = carBuilder.build();

        // Assert
        assertNotNull(car);
        assertEquals("BMW", car.getBrand());
        assertEquals("320i", car.getModel());
        assertEquals(new Power(200), car.getEngine().power());
        assertEquals(new Displacement(4), car.getEngine().displacement());
        assertEquals(FuelType.PETROL, car.getEngine().fuelType());
        assertTrue(car.getDetails().containsKey("Interior"));
        assertTrue(car.getDetails().containsKey("SteeringWheel"));
        assertTrue(car.getDetails().containsKey("Wheels"));
        assertTrue(car.getDetails().containsKey("Transmission"));
        assertEquals(new BigDecimal(4_500_000), car.getBasePrice());
        assertEquals(new BigDecimal(4_610_000), car.getFinalPrice());
    }

    @Test
    public void BuildCarWithoutDetailShouldThrownException() {

        // Arrange
        Engine engine = new Engine(new Power(200), new Displacement(4), FuelType.PETROL);
        Body body = new Sedan();
        BigDecimal price = new BigDecimal(4_500_000);

        Detail transmission = new TransmissionFactory().create("8AT", "BMW",
                BigDecimal.ZERO, List.of("BMW 320i", "BMW 330i"));
        Detail steeringWheel = new SteeringWheelFactory().create("Спортивный кожаный", "BMW",
                BigDecimal.ZERO, List.of("BMW 320i", "BMW 330i"));
        Detail wheels = new WheelsFactory().create("17’’ Standard", "BMW",
                BigDecimal.ZERO, List.of("BMW 320i"));

        CarBuilder carBuilder = new CarBuilder("BMW", "320i", engine, body, "White", price)
                .withSelectedDetail(transmission)
                .withSelectedDetail(steeringWheel)
                .withSelectedDetail(wheels);

        // Act
        Exception exception = assertThrows(DomainValidationException.class, carBuilder::build);

        // Assert
        assertEquals("You can't build car without necessary detail - Interior", exception.getMessage());
    }

    @Test
    public void BuildCarWithIncompatibleDetailShouldThrownException() {

        // Arrange
        Engine engine = new Engine(new Power(200), new Displacement(4), FuelType.PETROL);
        Body body = new Sedan();
        BigDecimal price = new BigDecimal(4_500_000);

        Detail transmission = new TransmissionFactory().create("8AT", "BMW",
                BigDecimal.ZERO, List.of("BMW 320i", "BMW 330i"));
        Detail steeringWheel = new SteeringWheelFactory().create("Спортивный кожаный", "BMW",
                BigDecimal.ZERO, List.of("BMW 320i", "BMW 330i"));
        Detail wheels = new WheelsFactory().create("17’’ Standard", "BMW",
                BigDecimal.ZERO, List.of("BMW 320i"));
        Detail interior = new InteriorFactory().create("Performance", "BMW",
                new BigDecimal(110_000), List.of("BMW 330i", "BMW 340i"));

        CarBuilder carBuilder = new CarBuilder("BMW", "320i", engine, body, "White", price)
                .withSelectedDetail(transmission)
                .withSelectedDetail(steeringWheel)
                .withSelectedDetail(wheels);

        // Act
        Exception exception = assertThrows(IncompatibleComponentException.class,
                () -> carBuilder.withSelectedDetail(interior));

        // Assert
        assertEquals("You can't suit this detail - Performance with this car - BMW 320i", exception.getMessage());
    }
}
