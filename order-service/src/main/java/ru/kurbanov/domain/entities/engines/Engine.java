package ru.kurbanov.domain.entities.engines;

import ru.kurbanov.domain.entities.cars.enums.FuelType;
import ru.kurbanov.domain.vo.Displacement;
import ru.kurbanov.domain.vo.Power;

public record Engine (Power power, Displacement displacement, FuelType fuelType) { }
