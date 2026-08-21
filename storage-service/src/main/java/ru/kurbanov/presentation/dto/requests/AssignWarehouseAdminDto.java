package ru.kurbanov.presentation.dto.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class AssignWarehouseAdminDto {
    private UUID warehouseAdminId;
}