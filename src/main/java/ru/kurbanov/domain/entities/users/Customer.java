package ru.kurbanov.domain.entities.users;

import lombok.Getter;
import ru.kurbanov.domain.entities.cars.Car;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class Customer {

    private final UUID id;
    private BigDecimal balance;

    public Customer() {
        this.id = UUID.randomUUID();
        this.balance = BigDecimal.ZERO;
    }

    public Customer(UUID id) {
        this.id = id;
        this.balance = BigDecimal.ZERO;
    }

    public boolean canBookCar(Car car) {
        return (this.balance.compareTo(car.getFinalPrice()) >= 0);
    }

    public boolean canTestDrive(Car car) {
        return true;
    }

    public boolean deposit(BigDecimal money) {
        this.balance = balance.add(money);
        return true;
    }
}
