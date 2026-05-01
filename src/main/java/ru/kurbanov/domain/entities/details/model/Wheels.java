package ru.kurbanov.domain.entities.details.model;

import ru.kurbanov.domain.entities.details.Detail;

import java.math.BigDecimal;
import java.util.List;

public class Wheels extends Detail {

    public Wheels(String name, BigDecimal price, List<String> models) {
        super(name, price, models);
    }

    @Override
    public String getType() {
        return "WHEELS";
    }
}
