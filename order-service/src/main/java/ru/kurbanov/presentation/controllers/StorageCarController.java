package ru.kurbanov.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kurbanov.application.services.CarGrpcClient;
import ru.kurbanov.presentation.dto.responses.StorageCarResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@Tag(name = "Storage Cars", description = "Get available cars from storage service vai grpc")
public class StorageCarController {

    private final CarGrpcClient carGrpcClient;

    @Operation(summary = "Get all available cars from storage")
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<List<StorageCarResponseDto>> listAvailableCars() {
        List<StorageCarResponseDto> cars = carGrpcClient.listAvailableCars()
                .stream()
                .map(
                        car -> new StorageCarResponseDto(
                                car.getId(),
                                car.getModelId(),
                                car.getVin(),
                                car.getStatus()
                        )
                )
                .toList();
        return ResponseEntity.ok(cars);
    }

    @Operation(summary = "Get available car by id from storage")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public ResponseEntity<StorageCarResponseDto> getCarById(@PathVariable String id) {
        return carGrpcClient.getCarById(id)
                .map(
                        car -> ResponseEntity.ok(new StorageCarResponseDto(
                                car.getId(),
                                car.getModelId(),
                                car.getVin(),
                                car.getStatus()
                        ))
                )
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
