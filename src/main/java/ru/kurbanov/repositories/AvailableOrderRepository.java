package ru.kurbanov.repositories;

import ru.kurbanov.domain.entities.orders.available.AvailableOrder;

import java.util.Collection;
import java.util.UUID;

public interface AvailableOrderRepository {

    AvailableOrder save(AvailableOrder order);
    void delete(AvailableOrder order);
    AvailableOrder findById(UUID id);
    Collection<AvailableOrder> show();
}
