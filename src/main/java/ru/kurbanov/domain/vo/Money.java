package ru.kurbanov.domain.vo;

public record Money(long value) {

    public static final Money ZERO = new Money(0);

    public Money plus(Money other) {
        return new Money(value + other.value);
    }

    public boolean isMoreOrEqual(Money other) {
        return this.value >= other.value;
    }
}
