package ru.kurbanov.presentation.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderResponseDto {

    private UUID id;
    private String orderType;
    private String orderStatus;
    private UUID orderedCar;
    private UUID customerId;
    private UUID managerId;
}
