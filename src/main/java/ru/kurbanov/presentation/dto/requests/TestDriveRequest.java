package ru.kurbanov.presentation.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class TestDriveRequest {

    @NotNull
    private UUID customerId;

    @NotNull
    private UUID orderedCar;

    @NotNull
    private ZonedDateTime startDate;
}
