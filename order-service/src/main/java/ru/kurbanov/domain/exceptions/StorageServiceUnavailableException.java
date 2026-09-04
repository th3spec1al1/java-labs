package ru.kurbanov.domain.exceptions;

public class StorageServiceUnavailableException extends RuntimeException {
    public StorageServiceUnavailableException(String message) {
        super(message);
    }
}
