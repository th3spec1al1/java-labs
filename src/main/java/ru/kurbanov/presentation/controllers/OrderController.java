package ru.kurbanov.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kurbanov.application.contracts.OrderService;
import ru.kurbanov.presentation.dto.requests.OrderFilterRequestDto;
import ru.kurbanov.presentation.dto.requests.OrderRequestDto;
import ru.kurbanov.presentation.dto.responses.OrderResponseDto;
import ru.kurbanov.presentation.dto.responses.ErrorResponseDto;

import java.util.List;
import java.util.UUID;

@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = OrderResponseDto.class)
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Incorrect request",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDto.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Not found",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDto.class)
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal error",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDto.class)
                )
        ),
})
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Get a order by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN') or (hasRole('USER') and @orderOwnerChecker.isOwner(#id))")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @Operation(summary = "Get a list of filtered orders")
    @PostMapping("/filter")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'USER')")
    public ResponseEntity<List<OrderResponseDto>> getOrders(@RequestBody OrderFilterRequestDto orderFilterRequestDto) {
        return ResponseEntity.ok(orderService.getOrders(orderFilterRequestDto));
    }

    @Operation(summary = "Add a order")
    @ApiResponse(
            responseCode = "201",
            description = "Created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderResponseDto.class)
            )
    )
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponseDto> addOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addOrder(orderRequestDto));
    }

    @Operation(summary = "Update the order")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable UUID id,
                                                        @RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderRequestDto));
    }

    @Operation(summary = "Remove order")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN') or (hasRole('USER') and @orderOwnerChecker.isOwner(#id))")
    public ResponseEntity<Void> removeOrder(@PathVariable UUID id) {
        orderService.removeOrder(id);
        return ResponseEntity.ok().build();
    }
}
