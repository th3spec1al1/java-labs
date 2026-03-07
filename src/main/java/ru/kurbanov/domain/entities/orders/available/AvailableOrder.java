package ru.kurbanov.domain.entities.orders.available;

import lombok.Getter;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.orders.Order;
import ru.kurbanov.domain.entities.orders.available.statuses.AvailableOrderStatus;
import ru.kurbanov.domain.entities.users.CarDealershipManager;
import ru.kurbanov.domain.entities.users.Customer;

public class AvailableOrder extends Order {

    @Getter
    private AvailableOrderStatus status;

    public AvailableOrder(AvailableOrderStatus status, Customer customer, CarDealershipManager manager, Car car) {
        super(customer, manager, car);
        this.status = status;
    }

    public void updateStatus(AvailableOrderStatus status) {
        this.status = status;
    }

    public boolean tryCreate(){
        return status.tryCreate(this);
    }

    public boolean tryAgreedByManager() {
        return status.tryAgreedByManager(this);
    }

    public boolean tryAwaitPay() {
        return status.tryAwaitPay(this);
    }

    public boolean tryPay() {
        return status.tryPay(this);
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
