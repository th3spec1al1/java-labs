package ru.kurbanov.application.contracts;

import ru.kurbanov.presentation.dto.requests.OrderFilterRequestDto;
import ru.kurbanov.presentation.dto.requests.OrderRequestDto;
import ru.kurbanov.presentation.dto.responses.OrderResponseDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponseDto getOrder(UUID orderId);

    List<OrderResponseDto> getOrders(OrderFilterRequestDto orderFilterRequestDto);

    OrderResponseDto addOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto updateOrder(UUID orderId, OrderRequestDto orderRequestDto);

    void removeOrder(UUID orderId);

    OrderResponseDto payOrder(UUID orderId);
}
