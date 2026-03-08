package ru.kurbanov.repositories.inmemory;

import ru.kurbanov.domain.entities.orders.available.AvailableOrder;
import ru.kurbanov.repositories.AvailableOrderRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryAvailableOrderRepository implements AvailableOrderRepository {

    private final Map<UUID, AvailableOrder> orders = new HashMap<>();

    @Override
    public AvailableOrder save(AvailableOrder order) {
        return orders.put(order.getId(), order);
    }

    @Override
    public void delete(AvailableOrder order) {
        orders.remove(order.getId());
    }

    @Override
    public AvailableOrder findById(UUID id) {
        return orders.get(id);
    }

    @Override
    public Collection<AvailableOrder> show() {
        return orders.values();
    }
}
