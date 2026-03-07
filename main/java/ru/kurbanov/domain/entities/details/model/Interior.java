package ru.kurbanov.domain.entities.details.model;

import lombok.Getter;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.vo.Money;

import java.util.List;

public class Interior extends Detail {

    @Getter
    private final String material;

    public Interior(String name, String brand, Money price, List<String> models, String material) {
        super(name, brand, price, models);
        this.material = material;
    }

    @Override
    public String getType() {
        return "Interior";
    }
}
