package ru.kurbanov.presentation.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TestDriveResponseDto {

    private UUID id;
    private UUID customerId;
    private UUID orderedCarId;
    private ZonedDateTime startDate;
}
