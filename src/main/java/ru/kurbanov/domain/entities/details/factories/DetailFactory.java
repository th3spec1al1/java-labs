package ru.kurbanov.domain.entities.details.factories;

import ru.kurbanov.domain.entities.details.Detail;

import java.math.BigDecimal;
import java.util.List;

public interface DetailFactory {
    Detail create(String name, BigDecimal price, List<String> models);
}
