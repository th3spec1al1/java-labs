package ru.kurbanov.domain.entities.details.factories.model;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.Transmission;
import ru.kurbanov.domain.entities.details.factories.DetailFactory;

import java.math.BigDecimal;
import java.util.List;

public class TransmissionFactory implements DetailFactory {

    @Override
    public Detail create(String name, BigDecimal price, List<String> models) {
        return new Transmission(name, price, models);
    }
}
