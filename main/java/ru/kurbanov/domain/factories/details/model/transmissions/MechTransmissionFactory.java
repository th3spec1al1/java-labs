package ru.kurbanov.domain.factories.details.model.transmissions;

import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.details.model.Transmission;
import ru.kurbanov.domain.enums.TransmissionType;
import ru.kurbanov.domain.factories.details.DetailFactory;
import ru.kurbanov.domain.vo.Money;

import java.util.List;

public class MechTransmissionFactory implements DetailFactory {

    @Override
    public Detail create() {
        return new Transmission("6MT", "BMW", new Money(-30000),
                List.of("320i", "330i"), TransmissionType.MECHANICAL);
    }
}
