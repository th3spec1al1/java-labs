package ru.kurbanov.domain.entities.cars;

import lombok.Getter;
import ru.kurbanov.domain.entities.bodies.Body;
import ru.kurbanov.domain.entities.engines.Engine;
import ru.kurbanov.domain.entities.restrictions.CarRestriction;
import ru.kurbanov.domain.entities.restrictions.model.UnitedRestrictions;
import ru.kurbanov.domain.vo.Money;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class CarFilter {

    private String brand;
    private String model;

    private Engine engine;
    private Body body;
    private String color;

    private Money price;

    public static Collection<Car> apply(Collection<Car> cars, CarRestriction restriction) {
        return cars.stream().filter(restriction::fits).collect(Collectors.toList());
    }

    public static Collection<Car> applyAll(Collection<Car> cars, CarRestriction... restrictions) {
        CarRestriction allRestrictions = restrictions[0];
        int i = 1;
        while (i < restrictions.length) {
            allRestrictions = new UnitedRestrictions(allRestrictions, restrictions[i]);
            i++;
        }
        return apply(cars, allRestrictions);
    }

    public CarFilter withBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public CarFilter withModel(String model) {
        this.model = model;
        return this;
    }

    public CarFilter withEngine(Engine engine) {
        this.engine = engine;
        return this;
    }

    public CarFilter withBody(Body body) {
        this.body = body;
        return this;
    }

    public CarFilter withColor(String color) {
        this.color = color;
        return this;
    }

    public CarFilter withPrice(Money price) {
        this.price = price;
        return this;
    }
}
