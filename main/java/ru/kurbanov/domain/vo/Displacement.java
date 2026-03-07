package ru.kurbanov.domain.vo;

public record Displacement(long value) {

    public Displacement {
        if (value < 0) {
            throw new IllegalArgumentException("Money can't be negative. Value: " + value);
        }
    }
}
