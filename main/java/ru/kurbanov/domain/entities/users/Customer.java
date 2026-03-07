package ru.kurbanov.domain.entities.users;

import lombok.Getter;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.vo.Money;

import java.util.UUID;

@Getter
public class Customer {

    private final UUID id;
    private Money balance;

    public Customer() {
        this.id = UUID.randomUUID();
        this.balance = Money.ZERO;
    }

    public boolean canBookCar(Car car) {
        return this.balance.isMoreOrEqual(car.getFinalPrice());
    }

    public boolean canTestDrive(Car car) {
        return true;
    }

    public boolean deposit(Money money) {
        this.balance = balance.plus(money);
        return true;
    }
}
