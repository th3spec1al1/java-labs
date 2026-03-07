package ru.kurbanov.domain.entities.details;

import lombok.Getter;
import ru.kurbanov.domain.vo.Money;

import java.util.List;
import java.util.UUID;

@Getter
public abstract class Detail {

    private final UUID id;
    private final String name;

    private final String brand;
    private final Money price;
    private final List<String> models;

    protected Detail(String name, String brand, Money price, List<String> models) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.models = models;
    }

    public abstract String getType();
}
