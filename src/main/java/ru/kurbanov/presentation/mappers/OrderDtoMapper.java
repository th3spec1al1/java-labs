package ru.kurbanov.presentation.mappers;

import org.mapstruct.Mapper;
import ru.kurbanov.domain.entities.orders.Order;
import ru.kurbanov.domain.entities.orders.available.AvailableOrder;
import ru.kurbanov.domain.entities.orders.available.statuses.model.CreatedAvailableOrderStatus;
import ru.kurbanov.domain.entities.orders.custom.CustomOrder;
import ru.kurbanov.domain.entities.orders.custom.statuses.model.CreatedCustomOrderStatus;
import ru.kurbanov.presentation.dto.requests.OrderRequestDto;
import ru.kurbanov.presentation.dto.responses.OrderResponseDto;

import java.util.UUID;

@Mapper(componentModel = "spring")
public class OrderDtoMapper {

    public OrderResponseDto toDto(Order order) {
        if (order == null) return null;

        return new OrderResponseDto(
                order.getId(),
                order.getOrderType(),
                order.getOrderStatus(),
                order.getCar().getId(),
                order.getCustomer().getId(),
                order.getManager().getId()
        );
    }

    public Order toDomain(UUID id, OrderRequestDto orderRequestDto) {
        if (orderRequestDto == null) return null;

        UUID orderId = id != null ? id : UUID.randomUUID();

        return switch (orderRequestDto.getOrderType().toUpperCase()) {
            case "AVAILABLE" -> new AvailableOrder(
                    orderId,
                    new CreatedAvailableOrderStatus(),
                    null,
                    null,
                    null
            );
            case "CUSTOM" -> new CustomOrder(
                    orderId,
                    new CreatedCustomOrderStatus(),
                    null,
                    null,
                    null
            );
            default -> throw new IllegalArgumentException(
                    "Unknown order type: " + orderRequestDto.getOrderType()
            );
        };
    }
}
