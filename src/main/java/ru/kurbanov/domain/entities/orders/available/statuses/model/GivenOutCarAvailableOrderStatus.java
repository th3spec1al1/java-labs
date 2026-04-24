package ru.kurbanov.domain.entities.orders.available.statuses.model;

import ru.kurbanov.domain.entities.orders.available.AvailableOrder;
import ru.kurbanov.domain.entities.orders.available.statuses.AvailableOrderStatus;

public class GivenOutCarAvailableOrderStatus implements AvailableOrderStatus {

    @Override
    public String getOrderStatus() { return "given out"; }

    @Override
    public boolean tryCreate(AvailableOrder order) {
        return false;
    }

    @Override
    public boolean tryAgreedByManager(AvailableOrder order){
        return false;
    }

    @Override
    public boolean tryAwaitPay(AvailableOrder order){
        return false;
    }

    @Override
    public boolean tryPay(AvailableOrder order){
        return false;
    }

    @Override
    public boolean tryGiveOutCar(AvailableOrder order){
        return false;
    }

    @Override
    public boolean tryComplete(AvailableOrder order){
        order.updateStatus(new CompletedAvailableOrderStatus());
        return true;
    }

    @Override
    public boolean tryCancel(AvailableOrder order){
        return false;
    }
}
