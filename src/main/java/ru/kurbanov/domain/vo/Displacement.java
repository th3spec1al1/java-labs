package ru.kurbanov.domain.vo;

public record Displacement(int value) {

    public Displacement {
        if (value < 0) {
            throw new IllegalArgumentException("Displacement can't be negative. Value: " + value);
        }
    }
}
