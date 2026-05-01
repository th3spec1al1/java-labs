package ru.kurbanov.presentation.dto.requests;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CarFilterRequestDto {

    private String brand;
    private String model;
    private String fuelType;
    private String carBody;
    private String carDrive;
    private String gearboxType;
    private String color;
    private BigDecimal maxPrice;
    private Integer minPower;
    private Integer minDisplacement;
    private UUID interiorId;
    private UUID steeringWheel;
    private UUID transmissionId;
    private UUID wheelsId;
}
