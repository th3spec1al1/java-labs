package ru.kurbanov.domain.entities.details.factories.model;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.Interior;
import ru.kurbanov.domain.entities.details.factories.DetailFactory;
import ru.kurbanov.domain.entities.details.model.Wheels;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class InteriorFactory implements DetailFactory {

    @Override
    public Detail create(String name, BigDecimal price, List<String> models) {
        return new Interior(name, price, models);
    }

    @Override
    public Detail create(UUID id, String name, BigDecimal price, List<String> models) {
        return new Interior(id, name, price, models);
    }
}
