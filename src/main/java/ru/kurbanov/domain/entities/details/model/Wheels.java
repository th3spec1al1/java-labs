package ru.kurbanov.domain.entities.details.model;

import lombok.Getter;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.vo.Money;

import java.util.List;

public class Wheels extends Detail {

    @Getter
    private final int diameter;

    public Wheels(String name, String brand, Money price, List<String> models, int diameter) {
        super(name, brand, price, models);
        this.diameter = diameter;
    }

    @Override
    public String getType() {
        return "Wheels";
    }
}
