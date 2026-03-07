package ru.kurbanov.domain.factories.details.model.interiors;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.Interior;
import ru.kurbanov.domain.factories.details.DetailFactory;
import ru.kurbanov.domain.vo.Money;

import java.util.List;

public class PerformanceInteriorFactory implements DetailFactory {

    @Override
    public Detail create() {
        return new Interior("Performance", "BMW", new Money(160000),
                List.of("330i", "340i"), "-");
    }
}
