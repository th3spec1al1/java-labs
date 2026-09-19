package ru.kurbanov.domain.entities.orders;

import lombok.Getter;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.users.CarDealershipManager;
import ru.kurbanov.domain.entities.users.Customer;

import java.util.UUID;

@Getter
public abstract class Order {

    private final UUID id;
    private final Customer customer;
    private final CarDealershipManager manager;
    private final Car car;

    public Order(Customer customer, CarDealershipManager manager, Car car) {
        this.id = UUID.randomUUID();
        this.customer = customer;
        this.manager = manager;
        this.car = car;
    }

    public Order(UUID id, Customer customer, CarDealershipManager manager, Car car) {
        this.id = id;
        this.customer = customer;
        this.manager = manager;
        this.car = car;
    }

    public abstract String getOrderStatus();

    public abstract String getOrderType();
}
