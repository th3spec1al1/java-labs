package ru.kurbanov.domain.vo;

public record Power(int value) {

    public Power {
        if (value < 0) {
            throw new IllegalArgumentException("Power can't be negative. Value: " + value);
        }
    }
}
