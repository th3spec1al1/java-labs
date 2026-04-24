package ru.kurbanov.repository;

import ru.kurbanov.domain.entities.details.Detail;

import java.util.Collection;
import java.util.UUID;

public interface DetailRepository {

    Detail save(Detail detail);
    void delete(Detail detail);
    Detail findById(UUID id);
    Collection<Detail> show();
}
