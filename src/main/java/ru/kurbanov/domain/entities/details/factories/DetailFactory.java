package ru.kurbanov.domain.entities.details.factories;

import ru.kurbanov.domain.entities.details.Detail;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface DetailFactory {
    Detail create(String name, BigDecimal price, List<String> models);

    Detail create(UUID id, String name, BigDecimal price, List<String> models);
}
