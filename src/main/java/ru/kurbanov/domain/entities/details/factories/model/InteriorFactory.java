package ru.kurbanov.domain.entities.details.factories.model;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.Interior;
import ru.kurbanov.domain.entities.details.factories.DetailFactory;

import java.math.BigDecimal;
import java.util.List;

public class InteriorFactory implements DetailFactory {

    @Override
    public Detail create(String name, BigDecimal price, List<String> models) {
        return new Interior(name, price, models);
    }
}
