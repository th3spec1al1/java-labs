package ru.kurbanov.presentation.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DetailRequestDto {

    @NotNull
    private String type;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    private List<String> compatibleModels;
}
