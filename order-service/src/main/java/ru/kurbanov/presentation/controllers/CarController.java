package ru.kurbanov.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kurbanov.application.contracts.CarService;
import ru.kurbanov.presentation.dto.requests.CarConfigureRequestDto;
import ru.kurbanov.presentation.dto.requests.CarFilterRequestDto;
import ru.kurbanov.presentation.dto.requests.CarRequestDto;
import ru.kurbanov.presentation.dto.responses.CarResponseDto;
import ru.kurbanov.presentation.dto.responses.ErrorResponseDto;

import java.util.List;
import java.util.UUID;

@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CarResponseDto.class)
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
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @Operation(summary = "Get a car by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDto> getCar(@PathVariable UUID id) {
        return ResponseEntity.ok(carService.getCar(id));
    }

    @Operation(summary = "Get a list of filtered cars")
    @PostMapping("/filter")
    public ResponseEntity<List<CarResponseDto>> getCars(@RequestBody CarFilterRequestDto carFilterRequestDto) {
        return ResponseEntity.ok(carService.getCars(carFilterRequestDto));
    }

    @Operation(summary = "Add a car")
    @ApiResponse(
            responseCode = "201",
            description = "Created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CarResponseDto.class)
            )
    )
    @PostMapping
    public ResponseEntity<CarResponseDto> addCar(@RequestBody CarRequestDto carRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.addCar(carRequestDto));
    }

    @Operation(summary = "Update the car")
    @PutMapping("/{id}")
    public ResponseEntity<CarResponseDto> updateCar(@PathVariable UUID id,
                                                    @RequestBody CarRequestDto carRequestDto) {
        return ResponseEntity.ok(carService.updateCar(id, carRequestDto));
    }

    @Operation(summary = "Remove car")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCar(@PathVariable UUID id) {
        carService.removeCar(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Configure the car")
    @ApiResponse(
            responseCode = "201",
            description = "Created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CarResponseDto.class)
            )
    )
    @PostMapping("/{id}/configure")
    public ResponseEntity<CarResponseDto> configureCar(@PathVariable UUID id,
                                                       @RequestBody CarConfigureRequestDto carConfigureRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.configureCar(id, carConfigureRequestDto));
    }
}
