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
import ru.kurbanov.application.contracts.TestDriveService;
import ru.kurbanov.presentation.dto.requests.TestDriveRequestDto;
import ru.kurbanov.presentation.dto.responses.TestDriveResponseDto;
import ru.kurbanov.presentation.dto.responses.ErrorResponseDto;

import java.util.List;
import java.util.UUID;

@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = TestDriveResponseDto.class)
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
@RequestMapping("/testDrives")
@RequiredArgsConstructor
public class TestDriveController {

    private final TestDriveService testDriveService;

    @Operation(summary = "Get a TestDrive by ID")
    @GetMapping("/{id}")
    public ResponseEntity<TestDriveResponseDto> getTestDrive(@PathVariable UUID id) {
        return ResponseEntity.ok(testDriveService.getTestDrive(id));
    }

    @Operation(summary = "Get a list of all TestDrives")
    @GetMapping
    public ResponseEntity<List<TestDriveResponseDto>> getTestDrives() {
        return ResponseEntity.ok(testDriveService.getTestDrives());
    }

    @Operation(summary = "Add a TestDrive")
    @ApiResponse(
            responseCode = "201",
            description = "Created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TestDriveResponseDto.class)
            )
    )
    @PostMapping
    public ResponseEntity<TestDriveResponseDto> addTestDrive(@RequestBody TestDriveRequestDto testDriveRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(testDriveService.addTestDrive(testDriveRequestDto));
    }

    @Operation(summary = "Update the TestDrive")
    @PutMapping("/{id}")
    public ResponseEntity<TestDriveResponseDto> updateTestDrive(@PathVariable UUID id,
                                                                @RequestBody TestDriveRequestDto testDriveRequestDto) {
        return ResponseEntity.ok(testDriveService.updateTestDrive(id, testDriveRequestDto));
    }

    @Operation(summary = "Remove TestDrive")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeTestDrive(@PathVariable UUID id) {
        testDriveService.removeTestDrive(id);
        return ResponseEntity.ok().build();
    }
}
