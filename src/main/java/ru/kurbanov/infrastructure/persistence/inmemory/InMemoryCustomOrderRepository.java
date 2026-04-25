package ru.kurbanov.infrastructure.persistence.inmemory;

import ru.kurbanov.domain.entities.orders.custom.CustomOrder;
import ru.kurbanov.repository.CustomOrderRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryCustomOrderRepository implements CustomOrderRepository {

    private final Map<UUID, CustomOrder> orders = new HashMap<>();

    @Override
    public CustomOrder save(CustomOrder order) {
        return orders.put(order.getId(), order);
    }

    @Override
    public void delete(CustomOrder order) {
        orders.remove(order.getId());
    }

    @Override
    public CustomOrder findById(UUID id) {
        return orders.get(id);
    }

    @Override
    public Collection<CustomOrder> show() {
        return orders.values();
    }
}