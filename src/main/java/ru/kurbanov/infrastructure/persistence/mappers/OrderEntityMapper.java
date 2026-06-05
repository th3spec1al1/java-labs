package ru.kurbanov.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.orders.Order;
import ru.kurbanov.domain.entities.orders.available.AvailableOrder;
import ru.kurbanov.domain.entities.orders.available.statuses.AvailableOrderStatus;
import ru.kurbanov.domain.entities.orders.available.statuses.model.*;
import ru.kurbanov.domain.entities.orders.custom.CustomOrder;
import ru.kurbanov.domain.entities.orders.custom.statuses.CustomOrderStatus;
import ru.kurbanov.domain.entities.orders.custom.statuses.model.*;
import ru.kurbanov.domain.entities.users.CarDealershipManager;
import ru.kurbanov.domain.entities.users.Customer;
import ru.kurbanov.infrastructure.persistence.jpa.model.OrderEntity;

@Component
public class OrderEntityMapper {

    public Order toDomain(OrderEntity orderEntity, Customer customer,
                          CarDealershipManager carDealershipManager, Car car) {
        if (orderEntity == null) return null;

        return switch (orderEntity.getOrderType().toUpperCase()) {
            case "AVAILABLE" -> new AvailableOrder(
                    orderEntity.getId(),
                    mapAvailableStatus(orderEntity.getOrderStatus()),
                    customer,
                    carDealershipManager,
                    car);
            case "CUSTOM" -> new CustomOrder(
                    orderEntity.getId(),
                    mapCustomStatus(orderEntity.getOrderStatus()),
                    customer,
                    carDealershipManager,
                    car);
            default -> throw new IllegalArgumentException(
                    "Unknown order type: " + orderEntity.getOrderType().toLowerCase());
        };
    }

    public OrderEntity toEntity(Order order) {
        if (order == null) return null;

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setOrderType(order.getOrderType());
        orderEntity.setOrderStatus(order.getOrderStatus());
        orderEntity.setCustomerId(order.getCustomer().getId());
        orderEntity.setManagerId(order.getManager().getId());
        orderEntity.setOrderedCarId(order.getCar().getId());

        return orderEntity;
    }

    private AvailableOrderStatus mapAvailableStatus(String status){
        if (status == null) return null;
        return switch (status.toUpperCase()) {
            case "AGREED" -> new AgreedByManagerAvailableOrderStatus();
            case "AWAITING_PAY" -> new AwaitingPayAvailableOrderStatus();
            case "CANCELLED" -> new CancelledAvailableOrderStatus();
            case "COMPLETED" -> new CompletedAvailableOrderStatus();
            case "CREATED" -> new CreatedAvailableOrderStatus();
            case "GIVEN_OUT" -> new GivenOutCarAvailableOrderStatus();
            case "PAID" -> new PaidAvailableOrderStatus();
            default -> throw new IllegalArgumentException("Unknown available order status: " + status);
        };
    }

    private CustomOrderStatus mapCustomStatus(String status){
        if (status == null) return null;
        return switch (status.toUpperCase()) {
            case "AGREED" -> new AgreedByWarehouseCustomOrderStatus();
            case "AWAITING_DELIVERY" -> new AwaitingDeliveryCustomOrderStatus();
            case "AWAITING_PAY" -> new AwaitingPayCustomOrderStatus();
            case "CANCELLED" -> new CancelledCustomOrderStatus();
            case "COMPLETED" -> new CompletedCustomOrderStatus();
            case "CREATED" -> new CreatedCustomOrderStatus();
            case "GIVEN_OUT" -> new GivenOutCarCustomOrderStatus();
            case "PAID" -> new PaidCustomOrderStatus();
            default -> throw new IllegalArgumentException("Unknown custom order status: " + status);
        };
    }
}
