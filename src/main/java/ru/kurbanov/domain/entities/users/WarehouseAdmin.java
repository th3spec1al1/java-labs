package ru.kurbanov.domain.entities.users;

import lombok.Getter;

import java.util.UUID;

@Getter
public class WarehouseAdmin {

    private final UUID id;

    public WarehouseAdmin() {
        this.id = UUID.randomUUID();
    }

    public WarehouseAdmin(UUID id) {
        this.id = id;
    }
}
