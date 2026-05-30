package ru.kurbanov.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kurbanov.TestCarDealershipApplication;
import ru.kurbanov.application.abstractions.repositories.jpa.JpaCarRepository;
import ru.kurbanov.infrastructure.persistence.specifications.CarSpecifications;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarRepositoryTest extends TestCarDealershipApplication {

    @Autowired
    private JpaCarRepository carRepository;

    @Test
    void shouldFindAllCars() {
        var cars = carRepository.findAll();
        assertThat(cars).isNotEmpty();
    }

    @Test
    void shouldFindByBran() {
        var cars = carRepository.findAll(CarSpecifications.hasBrand("BMW"));
        assertThat(cars).allMatch(c -> "BMW".equals(c.getBrand()));
    }
}
