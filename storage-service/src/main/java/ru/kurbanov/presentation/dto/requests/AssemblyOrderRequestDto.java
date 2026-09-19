package ru.kurbanov.presentation.dto.requests;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AssemblyOrderRequestDto {
    private UUID sourceOrderId;
    private String sourceOrderType;
    private UUID carId;
    private UUID modelId;
    private List<UUID> requiredComponentIds;
    private String status;
    private UUID warehouseAdminId;
}