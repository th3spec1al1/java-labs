package ru.kurbanov.domain.entities.details.model;

import lombok.Getter;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.vo.Money;

import java.util.List;

public class SteeringWheel extends Detail {

    @Getter
    private final boolean hasHeating;

    @Getter
    private final String material;

    public SteeringWheel(String name, String brand, Money price,
                         List<String> models, boolean hasHeating, String material) {
        super(name, brand, price, models);
        this.hasHeating = hasHeating;
        this.material = material;
    }

    @Override
    public String getType() {
        return "SteeringWheel";
    }
}
