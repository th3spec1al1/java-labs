package ru.kurbanov.application.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSentForApprovalEvent {
    private UUID orderId;
    private String orderType;
    private UUID carId;
    private String traceId;
}