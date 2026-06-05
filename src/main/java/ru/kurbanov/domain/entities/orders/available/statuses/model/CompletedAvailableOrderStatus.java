package ru.kurbanov.domain.entities.orders.available.statuses.model;

import ru.kurbanov.domain.entities.orders.available.AvailableOrder;
import ru.kurbanov.domain.entities.orders.available.statuses.AvailableOrderStatus;

public class CompletedAvailableOrderStatus implements AvailableOrderStatus {

    @Override
    public String getOrderStatus() { return "COMPLETED"; }

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
        return false;
    }

    @Override
    public boolean tryCancel(AvailableOrder order){
        return false;
    }
}
