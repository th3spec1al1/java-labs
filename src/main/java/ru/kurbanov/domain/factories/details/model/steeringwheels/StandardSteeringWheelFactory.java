package ru.kurbanov.domain.factories.details.model.steeringwheels;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.SteeringWheel;
import ru.kurbanov.domain.factories.details.DetailFactory;
import ru.kurbanov.domain.vo.Money;

import java.util.List;

public class StandardSteeringWheelFactory implements DetailFactory {

    @Override
    public Detail create() {
        return new SteeringWheel("Standard", "BMW", Money.ZERO,
                List.of("320i", "330i"), false, "Leather");
    }
}
