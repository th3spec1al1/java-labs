package ru.kurbanov.domain.entities.orders.available.statuses;

import ru.kurbanov.domain.entities.orders.available.AvailableOrder;

public interface AvailableOrderStatus {

    String getOrderStatus();

    boolean tryCreate(AvailableOrder order);
    boolean tryAgreedByManager(AvailableOrder order);
    boolean tryAwaitPay(AvailableOrder order);
    boolean tryPay(AvailableOrder order);
    boolean tryGiveOutCar(AvailableOrder order);
    boolean tryComplete(AvailableOrder order);
    boolean tryCancel(AvailableOrder order);
}
