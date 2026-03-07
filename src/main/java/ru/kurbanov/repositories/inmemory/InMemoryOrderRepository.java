package ru.kurbanov.repositories.inmemory;

import ru.kurbanov.domain.entities.orders.Order;
import ru.kurbanov.repositories.OrderRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryOrderRepository implements OrderRepository {

    private final Map<UUID, Order> orders = new HashMap<>();

    @Override
    public Order save(Order order) {
        return orders.put(order.getId(), order);
    }

    @Override
    public void delete(Order order) {
        orders.remove(order.getId());
    }

    @Override
    public Order findById(UUID id) {
        return orders.get(id);
    }

    @Override
    public Collection<Order> show() {
        return orders.values();
    }
}
