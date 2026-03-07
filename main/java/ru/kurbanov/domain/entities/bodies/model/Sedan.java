package ru.kurbanov.domain.entities.bodies.model;

import ru.kurbanov.domain.entities.bodies.Body;

public class Sedan implements Body {

    @Override
    public String getType() {
        return "Sedan";
    }
}
