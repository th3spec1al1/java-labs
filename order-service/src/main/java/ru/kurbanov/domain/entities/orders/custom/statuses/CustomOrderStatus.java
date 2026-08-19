package ru.kurbanov.domain.entities.orders.custom.statuses;

import ru.kurbanov.domain.entities.orders.custom.CustomOrder;

public interface CustomOrderStatus {

    String getOrderStatus();

    boolean tryCreate(CustomOrder order);
    boolean tryAgreedByWarehouse(CustomOrder order);
    boolean tryAwaitPay(CustomOrder order);
    boolean tryPay(CustomOrder order);
    boolean tryAwaitDelivery(CustomOrder order);
    boolean tryGiveOutCar(CustomOrder order);
    boolean tryComplete(CustomOrder order);
    boolean tryCancel(CustomOrder order);
}
