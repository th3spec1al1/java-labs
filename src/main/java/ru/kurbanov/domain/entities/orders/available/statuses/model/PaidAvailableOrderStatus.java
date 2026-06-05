package ru.kurbanov.domain.entities.orders.available.statuses.model;

import ru.kurbanov.domain.entities.orders.available.AvailableOrder;
import ru.kurbanov.domain.entities.orders.available.statuses.AvailableOrderStatus;

public class PaidAvailableOrderStatus implements AvailableOrderStatus {

    @Override
    public String getOrderStatus() { return "PAID"; }

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
        order.updateStatus(new GivenOutCarAvailableOrderStatus());
        return true;
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
