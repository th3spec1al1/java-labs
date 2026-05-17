package ru.kurbanov.presentation.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CarConfigureRequestDto {

    private UUID interiorId;
    private UUID steeringWheelId;
    private UUID transmissionId;
    private UUID wheelsId;
}
