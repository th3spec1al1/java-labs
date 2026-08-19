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
import ru.kurbanov.application.contracts.DetailService;
import ru.kurbanov.presentation.dto.requests.DetailRequestDto;
import ru.kurbanov.presentation.dto.responses.DetailResponseDto;
import ru.kurbanov.presentation.dto.responses.ErrorResponseDto;

import java.util.List;
import java.util.UUID;

@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = DetailResponseDto.class)
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
@RequestMapping("/details")
@RequiredArgsConstructor
public class DetailController {

    private final DetailService detailService;

    @Operation(summary = "Get a detail by ID")
    @GetMapping("/{id}")
    public ResponseEntity<DetailResponseDto> getDetail(@PathVariable UUID id) {
        return ResponseEntity.ok(detailService.getDetail(id));
    }

    @Operation(summary = "Get a list of all details")
    @GetMapping
    public ResponseEntity<List<DetailResponseDto>> getDetails() {
        return ResponseEntity.ok(detailService.getDetails());
    }

    @Operation(summary = "Add a detail")
    @ApiResponse(
            responseCode = "201",
            description = "Created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DetailResponseDto.class)
            )
    )
    @PostMapping
    public ResponseEntity<DetailResponseDto> addDetail(@RequestBody DetailRequestDto detailRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(detailService.addDetail(detailRequestDto));
    }

    @Operation(summary = "Update the detail")
    @PutMapping("/{id}")
    public ResponseEntity<DetailResponseDto> updateDetail(@PathVariable UUID id,
                                                          @RequestBody DetailRequestDto detailRequestDto) {
        return ResponseEntity.ok(detailService.updateDetail(id, detailRequestDto));
    }

    @Operation(summary = "Remove detail")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeDetail(@PathVariable UUID id) {
        detailService.removeDetail(id);
        return ResponseEntity.ok().build();
    }
}
