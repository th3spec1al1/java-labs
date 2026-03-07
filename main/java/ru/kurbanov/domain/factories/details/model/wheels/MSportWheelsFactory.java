package ru.kurbanov.domain.factories.details.model.wheels;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.Wheels;
import ru.kurbanov.domain.factories.details.DetailFactory;
import ru.kurbanov.domain.vo.Money;

import java.util.List;

public class MSportWheelsFactory implements DetailFactory {

    @Override
    public Detail create() {
        return new Wheels("M-Sport", "BMW", new Money(95000),
                List.of("320i", "330i", "340i"), 19);
    }
}
