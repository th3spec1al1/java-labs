package ru.kurbanov.domain.entities.details.model;

import ru.kurbanov.domain.entities.details.Detail;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class SteeringWheel extends Detail {

    public SteeringWheel(String name, BigDecimal price, List<String> models) {
        super(name, price, models);
    }

    public SteeringWheel(UUID id, String name, BigDecimal price, List<String> models) {
        super(id, name, price, models);
    }

    @Override
    public String getType() {
        return "STEERING_WHEEL";
    }
}
