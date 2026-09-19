package ru.kurbanov.presentation.exceptionhandlers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kurbanov.domain.exceptions.DomainValidationException;
import ru.kurbanov.domain.exceptions.IncompatibleComponentException;
import ru.kurbanov.domain.exceptions.StorageServiceUnavailableException;
import ru.kurbanov.presentation.dto.responses.ErrorResponseDto;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorResponseDto(
                                "Internal server error",
                                e.getMessage(),
                                LocalDateTime.now()
                        )
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorResponseDto(
                                "Not found",
                                e.getMessage(),
                                LocalDateTime.now()
                        )
                );
    }

    @ExceptionHandler(DomainValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleDomainValidationException(DomainValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorResponseDto(
                                "Bad request",
                                e.getMessage(),
                                LocalDateTime.now()
                        )
                );
    }

    @ExceptionHandler(IncompatibleComponentException.class)
    public ResponseEntity<ErrorResponseDto> handleIncompatibleComponentException(IncompatibleComponentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorResponseDto(
                                "Bad request",
                                e.getMessage(),
                                LocalDateTime.now()
                        )
                );
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(
            org.springframework.security.access.AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        new ErrorResponseDto(
                                "Access denied",
                                e.getMessage(),
                                LocalDateTime.now()
                        )
                );
    }

    @ExceptionHandler(StorageServiceUnavailableException.class)
    public ResponseEntity<ErrorResponseDto> handleStorageServiceUnavailable(StorageServiceUnavailableException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(
                        new ErrorResponseDto(
                                "Storage service unavailable",
                                e.getMessage(),
                                LocalDateTime.now()
                        )
                );
    }
}
