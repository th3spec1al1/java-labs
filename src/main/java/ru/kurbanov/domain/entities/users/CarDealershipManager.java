package ru.kurbanov.domain.entities.users;

import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Getter
public class CarDealershipManager {

    private final UUID id;

    public CarDealershipManager() {
        this.id = UUID.randomUUID();
    }
}
