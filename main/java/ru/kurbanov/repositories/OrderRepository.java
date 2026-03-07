package ru.kurbanov.repositories;

import ru.kurbanov.domain.entities.orders.Order;

import java.util.Collection;
import java.util.UUID;

public interface OrderRepository {

    Order save(Order order);
    void delete(Order order);
    Order findById(UUID id);
    Collection<Order> show();
}
