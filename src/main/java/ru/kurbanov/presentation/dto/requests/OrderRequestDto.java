package ru.kurbanov.presentation.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderRequestDto {

    @NotNull
    private String orderType;

    @NotNull
    private UUID orderedCarId;

    @NotNull
    private UUID customerId;
}
