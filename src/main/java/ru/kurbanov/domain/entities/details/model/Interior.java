package ru.kurbanov.domain.entities.details.model;

import ru.kurbanov.domain.entities.details.Detail;

import java.math.BigDecimal;
import java.util.List;

public class Interior extends Detail {

    public Interior(String name, String brand, BigDecimal price, List<String> models) {
        super(name, brand, price, models);
    }

    @Override
    public String getType() {
        return "INTERIOR";
    }
}
