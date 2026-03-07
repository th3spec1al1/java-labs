package ru.kurbanov.domain.factories.details.model.interiors;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.Interior;
import ru.kurbanov.domain.factories.details.DetailFactory;
import ru.kurbanov.domain.vo.Money;

import java.util.List;

public class GraphiteInteriorFactory implements DetailFactory {

    @Override
    public Detail create() {
        return new Interior("Graphite", "BMW", Money.ZERO,
                List.of("320i"), "Fabric");
    }
}
