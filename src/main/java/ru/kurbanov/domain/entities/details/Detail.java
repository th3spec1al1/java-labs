package ru.kurbanov.domain.entities.details;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
public abstract class Detail {

    private final UUID id;
    private final String name;

    private final String brand;
    private final BigDecimal price;
    private final List<String> compatibleModels;

    protected Detail(String name, String brand, BigDecimal price, List<String> models) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.compatibleModels = models;
    }

    public abstract String getType();
}
