package ru.kurbanov.presentation.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CarResponseDto {

    private String brand;
    private String model;
    private String fuelType;
    private String carBody;
    private String carDrive;
    private String gearboxType;
    private String color;
    private BigDecimal maxPrice;
    private int minPower;
    private int minDisplacement;
    private UUID interiorId;
    private UUID steeringWheel;
    private UUID transmissionId;
    private UUID wheelsId;
}
