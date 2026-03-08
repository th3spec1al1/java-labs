package ru.kurbanov.domain.entities.cars;

import lombok.Getter;
import ru.kurbanov.domain.entities.bodies.Body;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;
import ru.kurbanov.domain.entities.restrictions.model.*;
import ru.kurbanov.domain.enums.FuelType;
import ru.kurbanov.domain.vo.Displacement;
import ru.kurbanov.domain.vo.Power;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class CarFilter {

    private CarRestriction restriction = new EmptyRestriction();

    public Collection<Car> apply(Collection<Car> cars) {
        return cars.stream().filter(restriction::fits).collect(Collectors.toList());
    }

    public CarFilter withBrand(String brand) {
        restriction = new UnitedRestrictions(restriction, new BrandRestriction(brand));
        return this;
    }

    public CarFilter withModel(String model) {
        restriction = new UnitedRestrictions(restriction, new ModelRestriction(model));
        return this;
    }

    public CarFilter withBody(Body body) {
        restriction = new UnitedRestrictions(restriction, new BodyRestriction(body));
        return this;
    }

    public CarFilter withFuel(FuelType fuelType) {
        restriction = new UnitedRestrictions(restriction, new FuelRestriction(fuelType));
        return this;
    }

    public CarFilter withPower(Power power) {
        restriction = new UnitedRestrictions(restriction, new PowerRestriction(power));
        return this;
    }

    public CarFilter withDisplacement(Displacement displacement) {
        restriction = new UnitedRestrictions(restriction, new DisplacementRestriction(displacement));
        return this;
    }

    public CarFilter withColor(String color) {
        restriction = new UnitedRestrictions(restriction, new ColorRestriction(color));
        return this;
    }

    public CarFilter withPrice(BigDecimal price) {
        restriction = new UnitedRestrictions(restriction, new PriceRestriction(price));
        return this;
    }
}
