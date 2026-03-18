package ru.kurbanov.domain.entities.bodies;

import lombok.Getter;

@Getter
public abstract class Body {

    private final String type;

    protected Body(String type) {
        this.type = type;
    }
}
