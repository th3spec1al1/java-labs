package ru.kurbanov.domain.entities.orders.custom.statuses.model;

import ru.kurbanov.domain.entities.orders.custom.CustomOrder;
import ru.kurbanov.domain.entities.orders.custom.statuses.CustomOrderStatus;

public class PaidCustomOrderStatus implements CustomOrderStatus {

    @Override
    public String getOrderStatus() { return "paid"; }

    @Override
    public boolean tryCreate(CustomOrder order) {
        return false;
    }

    @Override
    public boolean tryAgreedByWarehouse(CustomOrder order) {
        return false;
    }

    @Override
    public boolean tryAwaitPay(CustomOrder order) {
        return false;
    }

    @Override
    public boolean tryPay(CustomOrder order) {
        return false;
    }

    @Override
    public boolean tryAwaitDelivery(CustomOrder order) {
        order.updateStatus(new AwaitingDeliveryCustomOrderStatus());
        return true;
    }

    @Override
    public boolean tryGiveOutCar(CustomOrder order) {
        return false;
    }

    @Override
    public boolean tryComplete(CustomOrder order) {
        return false;
    }

    @Override
    public boolean tryCancel(CustomOrder order) {
        return false;
    }
}
