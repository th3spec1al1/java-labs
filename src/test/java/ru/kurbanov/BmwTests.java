package ru.kurbanov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.kurbanov.domain.builders.CarBuilder;
import ru.kurbanov.domain.entities.bodies.model.Sedan;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.Interior;
import ru.kurbanov.domain.entities.details.model.SteeringWheel;
import ru.kurbanov.domain.entities.details.model.Transmission;
import ru.kurbanov.domain.entities.details.model.Wheels;
import ru.kurbanov.domain.entities.engines.Engine;
import ru.kurbanov.domain.enums.FuelType;
import ru.kurbanov.domain.factories.details.model.InteriorFactory;
import ru.kurbanov.domain.factories.details.model.SteeringWheelFactory;
import ru.kurbanov.domain.factories.details.model.TransmissionFactory;
import ru.kurbanov.domain.factories.details.model.WheelsFactory;
import ru.kurbanov.domain.vo.Displacement;
import ru.kurbanov.domain.vo.Power;

import java.math.BigDecimal;
import java.util.List;

public class BmwTests {

    @Test
    public void BuildBmw320iShouldBuiltAndReturnSuccess(){
        // Act
        Car car = new CarBuilder("BMW", "320i",
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
                .build();

        // Assert
        Assertions.assertNotNull(car);
        Assertions.assertEquals(4500000, car.getBasePrice().intValue());
        Assertions.assertEquals("BMW", car.getBrand());

        Assertions.assertNotNull(car.getEngine());
        Assertions.assertTrue(car.getDetails().containsKey("Interior"));
        Assertions.assertTrue(car.getDetails().containsKey("SteeringWheel"));
        Assertions.assertTrue(car.getDetails().containsKey("Wheels"));
        Assertions.assertTrue(car.getDetails().containsKey("Transmission"));

        // Arrange
        Detail transmission = new Transmission("Mechanical 6MT",
                "BMW", new BigDecimal(-30000), List.of("320i"));

        Car newCar = new CarBuilder("BMW", "320i",
                        new Engine(new Power(184), new Displacement(2), FuelType.PETROL),
                new Sedan(), "White",
                new BigDecimal(4500000))
                .withSelectedDetail(new Wheels("17'' Standard", "BMW", new BigDecimal(0), List.of("320i")))
                .withSelectedDetail(transmission)
                .withSelectedDetail(new SteeringWheel("Standard", "BMW", new BigDecimal(0), List.of("320i", "330i")))
                .withSelectedDetail(new Interior("Textile Graphite", "BMW", new BigDecimal(0), List.of("320i")))
                .build();

        // Assert
        Assertions.assertEquals(4470000, newCar.getFinalPrice().intValue());
        Assertions.assertEquals("BMW", newCar.getBrand());

        Assertions.assertNotNull(newCar.getEngine());
        Assertions.assertEquals(4, newCar.getDetails().size());
        Assertions.assertTrue(newCar.getDetails().containsKey("Interior"));
        Assertions.assertTrue(newCar.getDetails().containsKey("SteeringWheel"));
        Assertions.assertTrue(newCar.getDetails().containsKey("Wheels"));
        Assertions.assertTrue(newCar.getDetails().containsKey("Transmission"));
    }
}
