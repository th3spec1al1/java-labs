package ru.kurbanov.repositories.inmemory;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.repositories.DetailRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryDetailRepository implements DetailRepository {

    private final Map<UUID, Detail> details = new HashMap<>();

    @Override
    public Detail save(Detail detail) {
        return details.put(detail.getId(), detail);
    }

    @Override
    public void delete(Detail detail) {
        details.remove(detail.getId());
    }

    @Override
    public Detail findById(UUID id) {
        return details.get(id);
    }

    @Override
    public Collection<Detail> show() {
        return details.values();
    }
}
