package ru.kurbanov.application.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSentForApprovalEvent {
    private UUID eventId;
    private String traceId;
    private UUID orderId;
    private String orderType;
    private UUID clientId;
    private UUID carId;
    private UUID modelId;
    private List<UUID> requiredComponentIds;
}