package ru.kurbanov.application.contracts;

import ru.kurbanov.presentation.dto.requests.AssemblyOrderRequestDto;
import ru.kurbanov.presentation.dto.responses.AssemblyOrderResponseDto;

import java.util.List;
import java.util.UUID;

public interface AssemblyOrderService {

    AssemblyOrderResponseDto create(AssemblyOrderRequestDto request);

    List<AssemblyOrderResponseDto> getAll();

    AssemblyOrderResponseDto getById(UUID id);

    AssemblyOrderResponseDto update(UUID id, AssemblyOrderRequestDto request);

    void delete(UUID id);

    AssemblyOrderResponseDto updateStatus(UUID id, String status);

    AssemblyOrderResponseDto assignWarehouseAdmin(UUID id, UUID warehouseAdminId);
}