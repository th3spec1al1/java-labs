package ru.kurbanov.application.abstractions.repositories.inmemory;

import ru.kurbanov.domain.entities.orders.custom.CustomOrder;

import java.util.Collection;
import java.util.UUID;

public interface CustomOrderRepository {

    CustomOrder save(CustomOrder order);
    void delete(CustomOrder order);
    CustomOrder findById(UUID id);
    Collection<CustomOrder> show();
}