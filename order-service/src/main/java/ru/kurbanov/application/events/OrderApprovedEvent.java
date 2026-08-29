package ru.kurbanov.application.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderApprovedEvent {

    private UUID orderId;
    private String traceId;
}
