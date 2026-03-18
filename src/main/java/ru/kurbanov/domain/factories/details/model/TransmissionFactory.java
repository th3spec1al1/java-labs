package ru.kurbanov.domain.factories.details.model;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.Transmission;
import ru.kurbanov.domain.factories.details.DetailFactory;

import java.math.BigDecimal;
import java.util.List;

public class TransmissionFactory implements DetailFactory {

    @Override
    public Detail create(String name, String brand, BigDecimal price, List<String> models) {
        return new Transmission(name, brand, price, models);
    }
}
