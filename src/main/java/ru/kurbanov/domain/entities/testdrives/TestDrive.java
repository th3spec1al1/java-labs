package ru.kurbanov.domain.entities.testdrives;

import lombok.Getter;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.users.Customer;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
public class TestDrive {

    private final UUID id;
    private final Customer customer;
    private final Car car;
    private final ZonedDateTime date;

    public TestDrive(Customer customer, Car car, ZonedDateTime date) {
        this.id = UUID.randomUUID();
        this.customer = customer;
        this.car = car;
        this.date = date;
    }
}
