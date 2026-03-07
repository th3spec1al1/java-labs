package ru.kurbanov.domain.factories.details.model.transmissions;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.Transmission;
import ru.kurbanov.domain.enums.TransmissionType;
import ru.kurbanov.domain.factories.details.DetailFactory;
import ru.kurbanov.domain.vo.Money;

import java.util.List;

public class AutoTransmissionFactory implements DetailFactory {

    @Override
    public Detail create() {
        return new Transmission("8AT", "BMW", Money.ZERO,
                List.of("320i", "330i"), TransmissionType.AUTOMATIC);
    }
}
