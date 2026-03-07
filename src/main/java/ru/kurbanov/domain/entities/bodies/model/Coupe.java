package ru.kurbanov.domain.entities.bodies.model;

import ru.kurbanov.domain.entities.bodies.Body;

public class Coupe implements Body {

    @Override
    public String getType() {
        return "Coupe";
    }
}
