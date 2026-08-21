package ru.kurbanov.domain.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AssemblyOrder {

    private UUID id;
    private UUID sourceOrderId;
    private String status;
}