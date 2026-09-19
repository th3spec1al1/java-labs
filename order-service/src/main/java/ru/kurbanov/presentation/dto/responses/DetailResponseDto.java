package ru.kurbanov.presentation.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class DetailResponseDto {

    private UUID id;
    private String type;
    private String name;
    private BigDecimal price;
    private List<String> compatibleModels;
}
