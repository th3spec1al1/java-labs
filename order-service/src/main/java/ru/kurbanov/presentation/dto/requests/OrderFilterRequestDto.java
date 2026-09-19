package ru.kurbanov.presentation.dto.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderFilterRequestDto {

    private String orderType;
    private String orderStatus;
    private UUID customerId;
    private UUID managerId;
    private UUID orderedCarId;
}
