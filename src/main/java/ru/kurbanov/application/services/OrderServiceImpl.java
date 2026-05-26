package ru.kurbanov.application.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kurbanov.application.abstractions.repositories.jpa.JpaCarRepository;
import ru.kurbanov.application.abstractions.repositories.jpa.JpaOrderRepository;
import ru.kurbanov.application.contracts.OrderService;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.orders.Order;
import ru.kurbanov.domain.entities.orders.available.AvailableOrder;
import ru.kurbanov.domain.entities.orders.available.statuses.model.CreatedAvailableOrderStatus;
import ru.kurbanov.domain.entities.orders.custom.CustomOrder;
import ru.kurbanov.domain.entities.orders.custom.statuses.model.CreatedCustomOrderStatus;
import ru.kurbanov.domain.entities.users.CarDealershipManager;
import ru.kurbanov.domain.entities.users.Customer;
import ru.kurbanov.infrastructure.persistence.jpa.model.CarEntity;
import ru.kurbanov.infrastructure.persistence.jpa.model.OrderEntity;
import ru.kurbanov.infrastructure.persistence.mappers.CarEntityMapper;
import ru.kurbanov.infrastructure.persistence.mappers.OrderEntityMapper;
import ru.kurbanov.infrastructure.persistence.specifications.OrderSpecifications;
import ru.kurbanov.presentation.dto.requests.OrderFilterRequestDto;
import ru.kurbanov.presentation.dto.requests.OrderRequestDto;
import ru.kurbanov.presentation.dto.responses.OrderResponseDto;
import ru.kurbanov.presentation.mappers.OrderDtoMapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final JpaOrderRepository orderRepository;
    private final JpaCarRepository carRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final CarEntityMapper carEntityMapper;
    private final OrderDtoMapper orderDtoMapper;
    private final CarDetailsLoader carDetailsLoader;

    private OrderResponseDto toDto(OrderEntity orderEntity) {
        Customer customer = new Customer(orderEntity.getCustomerId());
        CarDealershipManager manager = new CarDealershipManager(orderEntity.getManagerId());
        CarEntity carEntity = carRepository.getReferenceById(orderEntity.getOrderedCarId());
        Map<String, Detail> details = carDetailsLoader.loadDetails(carEntity);
        Car car = carEntityMapper.toDomain(carEntity, details);

        Order order = orderEntityMapper.toDomain(orderEntity, customer, manager, car);
        return orderDtoMapper.toDto(order);
    }

    @Override
    public OrderResponseDto getOrder(UUID orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));
        return toDto(orderEntity);
    }

    @Override
    public List<OrderResponseDto> getOrders(OrderFilterRequestDto orderFilterRequestDto) {
        Specification<OrderEntity> spec = OrderSpecifications.buildFilter(
                orderFilterRequestDto.getOrderType(),
                orderFilterRequestDto.getOrderStatus(),
                orderFilterRequestDto.getCustomerId(),
                orderFilterRequestDto.getManagerId(),
                orderFilterRequestDto.getOrderedCarId()
        );
        return orderRepository.findAll(spec).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public OrderResponseDto addOrder(OrderRequestDto orderRequestDto) {
        Customer customer = new Customer(orderRequestDto.getCustomerId());
        CarDealershipManager manager = new CarDealershipManager();
        CarEntity carEntity = carRepository.findById(orderRequestDto.getOrderedCarId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found: " + orderRequestDto.getOrderedCarId()));
        Map<String, Detail> details = carDetailsLoader.loadDetails(carEntity);
        Car car = carEntityMapper.toDomain(carEntity, details);

        Order order;
        if ("CUSTOM".equalsIgnoreCase(orderRequestDto.getOrderType())) {
            order = new CustomOrder(new CreatedCustomOrderStatus(), customer, manager, car);
        } else if ("AVAILABLE".equalsIgnoreCase(orderRequestDto.getOrderType())) {
            order = new AvailableOrder(new CreatedAvailableOrderStatus(), customer, manager, car);
        } else {
            throw new IllegalArgumentException("Non-existent order type: " + orderRequestDto.getOrderType());
        }

        OrderEntity orderEntity = orderEntityMapper.toEntity(order);
        OrderEntity saved = orderRepository.save(orderEntity);
        return toDto(saved);
    }

    @Override
    public OrderResponseDto updateOrder(UUID orderId, OrderRequestDto orderRequestDto) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));
        orderEntity.setOrderType(orderRequestDto.getOrderType());
        orderEntity.setOrderedCarId(orderRequestDto.getOrderedCarId());
        orderEntity.setCustomerId(orderRequestDto.getCustomerId());

        return toDto(orderEntity);
    }

    @Override
    public void removeOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }
}
