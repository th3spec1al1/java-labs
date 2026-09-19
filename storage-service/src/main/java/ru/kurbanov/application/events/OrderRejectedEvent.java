package ru.kurbanov.application.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRejectedEvent {

    private UUID orderId;
    private String reason;
    private String traceId;
}
