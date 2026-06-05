package ru.kurbanov.domain.entities.details.factories.model;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.Transmission;
import ru.kurbanov.domain.entities.details.factories.DetailFactory;
import ru.kurbanov.domain.entities.details.model.Wheels;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class TransmissionFactory implements DetailFactory {

    @Override
    public Detail create(String name, BigDecimal price, List<String> models) {
        return new Transmission(name, price, models);
    }

    @Override
    public Detail create(UUID id, String name, BigDecimal price, List<String> models) {
        return new Transmission(id, name, price, models);
    }
}
