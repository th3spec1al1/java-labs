package ru.kurbanov.presentation.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StorageCarResponseDto {

    private String id;
    private String modelId;
    private String vin;
    private String status;
}
