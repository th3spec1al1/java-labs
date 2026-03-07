package ru.kurbanov.domain.factories.details.model.interiors;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.Interior;
import ru.kurbanov.domain.factories.details.DetailFactory;
import ru.kurbanov.domain.vo.Money;

import java.util.List;

public class DakotaInteriorFactory implements DetailFactory {

    @Override
    public Detail create() {
        return new Interior("Dakota", "BMW", new Money(110000),
                List.of("320i", "330i"), "Leather");
    }
}
