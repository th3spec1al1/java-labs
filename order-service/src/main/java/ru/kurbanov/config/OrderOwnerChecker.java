package ru.kurbanov.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kurbanov.application.abstractions.repositories.jpa.JpaOrderRepository;

import java.util.UUID;

@Component("orderOwnerChecker")
@RequiredArgsConstructor
public class OrderOwnerChecker {

    private final JpaOrderRepository orderRepository;
    private final SecurityUtils securityUtils;

    public boolean isOwner(UUID orderId) {
        UUID currentUserId = securityUtils.getCurrentUserId();
        return orderRepository
                .findById(orderId)
                .map(order -> order.getCustomerId().equals(currentUserId))
                .orElse(false);
    }
}
