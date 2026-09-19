package ru.kurbanov.domain.entities.users;

import lombok.Getter;

import java.util.UUID;

@Getter
public class SystemAdmin {

    private final UUID id;

    public SystemAdmin() {
        this.id = UUID.randomUUID();
    }

    public SystemAdmin(UUID id) {
        this.id = id;
    }
}
