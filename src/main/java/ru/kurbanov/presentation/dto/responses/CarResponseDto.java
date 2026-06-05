package ru.kurbanov.presentation.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CarResponseDto {

    private UUID id;
    private String brand;
    private String model;
    private String fuelType;
    private String carBody;
    private String carDrive;
    private String gearboxType;
    private String color;
    private int enginePower;
    private int engineDisplacement;
    private BigDecimal basePrice;
    private UUID interiorId;
    private UUID steeringWheelId;
    private UUID transmissionId;
    private UUID wheelsId;
}
