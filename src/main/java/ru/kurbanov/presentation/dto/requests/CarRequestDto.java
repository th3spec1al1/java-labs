package ru.kurbanov.presentation.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CarRequestDto {

    @NotNull
    private String brand;

    @NotNull
    private String model;

    @NotNull
    private String fuelType;

    @NotNull
    private String carBody;

    @NotNull
    private String carDrive;

    @NotNull
    private String gearboxType;

    @NotNull
    private String color;

    @NotNull
    private BigDecimal maxPrice;

    @NotNull
    private int minPower;

    @NotNull
    private int minDisplacement;

    @NotNull
    private UUID interiorId;

    @NotNull
    private UUID steeringWheel;

    @NotNull
    private UUID transmissionId;

    @NotNull
    private UUID wheelsId;
}
