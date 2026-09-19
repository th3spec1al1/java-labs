package ru.kurbanov.domain.entities.orders.custom;

import lombok.Getter;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.orders.Order;
import ru.kurbanov.domain.entities.orders.custom.statuses.CustomOrderStatus;
import ru.kurbanov.domain.entities.users.CarDealershipManager;
import ru.kurbanov.domain.entities.users.Customer;

import java.util.UUID;

public class CustomOrder extends Order {

    @Getter
    private CustomOrderStatus status;

    public CustomOrder(CustomOrderStatus status, Customer customer, CarDealershipManager manager, Car car) {
        super(customer, manager, car);
        this.status = status;
    }

    public CustomOrder(UUID id, CustomOrderStatus status, Customer customer, CarDealershipManager manager, Car car) {
        super(id, customer, manager, car);
        this.status = status;
    }

    @Override
    public String getOrderStatus() {
        return status.getOrderStatus();
    }

    @Override
    public String getOrderType() {
        return "CUSTOM";
    }

    public void updateStatus(CustomOrderStatus status) {
        this.status = status;
    }

    public boolean tryCreate(){
        return status.tryCreate(this);
    }

    public boolean tryAgreedByManager() {
        return status.tryAgreedByWarehouse(this);
    }

    public boolean tryAwaitPay() {
        return status.tryAwaitPay(this);
    }

    public boolean tryPay() {
        return status.tryPay(this);
    }

    public boolean tryAwaitDelivery() {
        return status.tryAwaitDelivery(this);
    }

    public boolean tryGiveOutCar() {
        return status.tryGiveOutCar(this);
    }

    public boolean tryComplete() {
        return status.tryComplete(this);
    }

    public boolean tryCancel() {
        return status.tryCancel(this);
    }
}
