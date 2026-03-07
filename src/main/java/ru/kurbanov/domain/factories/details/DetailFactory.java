package ru.kurbanov.domain.factories.details;

import ru.kurbanov.domain.entities.details.Detail;

import java.math.BigDecimal;
import java.util.List;

public interface DetailFactory {
    Detail create(String name, String brand, BigDecimal price, List<String> models);
}
