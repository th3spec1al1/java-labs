package ru.kurbanov.presentation.dto.responses;

import java.time.LocalDateTime;

public record ErrorResponseDto(String message, String detailedMessage, LocalDateTime time) {}
