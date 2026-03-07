package ru.kurbanov.domain.factories.details.model.steeringwheels;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.SteeringWheel;
import ru.kurbanov.domain.factories.details.DetailFactory;
import ru.kurbanov.domain.vo.Money;

import java.util.List;

public class MSportSteeringWheelFactory implements DetailFactory {

    @Override
    public Detail create() {
        return new SteeringWheel("M-Sport", "BMW", new Money(25000),
                List.of("320i", "330i", "340i"), true, "-");
    }
}
