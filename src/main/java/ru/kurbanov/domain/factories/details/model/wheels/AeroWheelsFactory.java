package ru.kurbanov.domain.factories.details.model.wheels;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.Wheels;
import ru.kurbanov.domain.factories.details.DetailFactory;
import ru.kurbanov.domain.vo.Money;

import java.util.List;

public class AeroWheelsFactory implements DetailFactory {

    @Override
    public Detail create() {
        return new Wheels("Aero", "BMW", new Money(45000),
                List.of("320i", "330i"), 18);
    }
}
