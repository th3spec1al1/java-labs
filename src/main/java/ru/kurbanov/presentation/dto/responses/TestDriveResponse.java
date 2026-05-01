package ru.kurbanov.presentation.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TestDriveResponse {

    private UUID id;
    private UUID customerId;
    private UUID orderedCar;
    private ZonedDateTime startDate;
}
