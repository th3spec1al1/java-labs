package ru.kurbanov.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kurbanov.application.contracts.AssemblyOrderService;
import ru.kurbanov.presentation.dto.requests.AssemblyOrderRequestDto;
import ru.kurbanov.presentation.dto.requests.AssignWarehouseAdminDto;
import ru.kurbanov.presentation.dto.requests.UpdateAssemblyOrderStatusDto;
import ru.kurbanov.presentation.dto.responses.AssemblyOrderResponseDto;
import ru.kurbanov.presentation.dto.responses.ErrorResponseDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assembly-orders")
@RequiredArgsConstructor
@Tag(name = "Assembly orders", description = "Operations with assembly orders")
public class AssemblyOrderController {

    private final AssemblyOrderService assemblyOrderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    @Operation(summary = "Create an assembly order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public AssemblyOrderResponseDto create(@RequestBody AssemblyOrderRequestDto request) {
        return assemblyOrderService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    @Operation(summary = "Get all assembly orders")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public List<AssemblyOrderResponseDto> getAll() {
        return assemblyOrderService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    @Operation(summary = "Get an assembly order by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public AssemblyOrderResponseDto getById(
            @Parameter(description = "Assembly order ID") @PathVariable UUID id) {
        return assemblyOrderService.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    @Operation(summary = "Update an assembly order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public AssemblyOrderResponseDto update(
            @Parameter(description = "Assembly order ID") @PathVariable UUID id,
            @RequestBody AssemblyOrderRequestDto request) {
        return assemblyOrderService.update(id, request);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    @Operation(summary = "Update assembly order status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status updated"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public AssemblyOrderResponseDto updateStatus(
            @Parameter(description = "Assembly order ID") @PathVariable UUID id,
            @RequestBody UpdateAssemblyOrderStatusDto request) {
        return assemblyOrderService.updateStatus(id, request.getStatus());
    }

    @PatchMapping("/{id}/assignee")
    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    @Operation(summary = "Assign warehouse admin to assembly order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assigned"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public AssemblyOrderResponseDto assignWarehouseAdmin(
            @Parameter(description = "Assembly order ID") @PathVariable UUID id,
            @RequestBody AssignWarehouseAdminDto request) {
        return assemblyOrderService.assignWarehouseAdmin(id, request.getWarehouseAdminId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    @Operation(summary = "Delete an assembly order")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deleted"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public void delete(
            @Parameter(description = "Assembly order ID") @PathVariable UUID id) {
        assemblyOrderService.delete(id);
    }
}