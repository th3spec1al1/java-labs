package ru.kurbanov.application.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kurbanov.application.contracts.AssemblyOrderService;
import ru.kurbanov.infrastructure.persistence.jpa.JpaAssemblyOrderRepository;
import ru.kurbanov.infrastructure.persistence.jpa.model.AssemblyOrderEntity;
import ru.kurbanov.presentation.dto.requests.AssemblyOrderRequestDto;
import ru.kurbanov.presentation.dto.responses.AssemblyOrderResponseDto;
import ru.kurbanov.presentation.mappers.AssemblyOrderMapper;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AssemblyOrderServiceImpl implements AssemblyOrderService {

    private final JpaAssemblyOrderRepository repository;
    private final AssemblyOrderMapper mapper;

    @Override
    public AssemblyOrderResponseDto create(AssemblyOrderRequestDto request) {
        AssemblyOrderEntity entity = mapper.toEntity(request);
        entity.setStatus("CREATED");
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public List<AssemblyOrderResponseDto> getAll() {
        return repository.findAll().stream()
                .filter(e -> !e.isRemoved())
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public AssemblyOrderResponseDto getById(UUID id) {
        return repository.findById(id)
                .filter(e -> !e.isRemoved())
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Assembly order not found: " + id));
    }

    @Override
    public AssemblyOrderResponseDto update(UUID id, AssemblyOrderRequestDto request) {
        AssemblyOrderEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Assembly order not found: " + id));
        entity.setStatus(request.getStatus());
        return mapper.toDto(entity);
    }

    @Override
    public void delete(UUID id) {
        AssemblyOrderEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Assembly order not found: " + id));
        entity.setRemoved(true);
    }

    @Override
    public AssemblyOrderResponseDto updateStatus(UUID id, String status) {
        AssemblyOrderEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Assembly order not found: " + id));
        entity.setStatus(status);
        return mapper.toDto(entity);
    }

    @Override
    public AssemblyOrderResponseDto assignWarehouseAdmin(UUID id, UUID warehouseAdminId) {
        AssemblyOrderEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Assembly order not found: " + id));
        entity.setWarehouseAdminId(warehouseAdminId);
        return mapper.toDto(entity);
    }
}