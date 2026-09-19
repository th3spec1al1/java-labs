package ru.kurbanov.presentation.dto.responses;

import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class AssemblyOrderResponseDto {
    private UUID id;
    private UUID sourceOrderId;
    private String sourceOrderType;
    private UUID carId;
    private UUID modelId;
    private List<UUID> requiredComponentIds;
    private String status;
    private UUID warehouseAdminId;
    private Instant createdAt;
    private Instant updatedAt;
}