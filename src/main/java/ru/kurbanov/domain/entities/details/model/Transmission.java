package ru.kurbanov.domain.entities.details.model;

import lombok.Getter;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.enums.TransmissionType;
import ru.kurbanov.domain.vo.Money;

import java.util.List;

public class Transmission extends Detail {

    @Getter
    private final TransmissionType transmissionType;

    public Transmission(String name, String brand, Money price,
                        List<String> models, TransmissionType transmissionType) {
        super(name, brand, price, models);
        this.transmissionType = transmissionType;
    }

    @Override
    public String getType() {
        return "Transmission";
    }
}
