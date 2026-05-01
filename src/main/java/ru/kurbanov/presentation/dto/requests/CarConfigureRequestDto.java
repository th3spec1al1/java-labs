package ru.kurbanov.presentation.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CarConfigureRequestDto {

    @NotNull
    private UUID interiorId;

    @NotNull
    private UUID steeringWheelId;

    @NotNull
    private UUID transmissionId;

    @NotNull
    private UUID wheelsId;
}
