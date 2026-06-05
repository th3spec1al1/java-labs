package ru.kurbanov.infrastructure.persistence.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.kurbanov.infrastructure.persistence.jpa.model.OrderEntity;

import java.util.UUID;

public class OrderSpecifications {

    public static Specification<OrderEntity> hasOrderType(String orderType) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("orderType"), orderType));
    }

    public static Specification<OrderEntity> hasOrderStatus(String orderStatus) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("orderStatus"), orderStatus));
    }

    public static Specification<OrderEntity> hasCustomerId(UUID customerId) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("customerId"), customerId));
    }

    public static Specification<OrderEntity> hasManagerId(UUID managerId) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("managerId"), managerId));
    }

    public static Specification<OrderEntity> hasOrderedCarId(UUID orderedCarId) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("orderedCarId"), orderedCarId));
    }

    public static Specification<OrderEntity> buildFilter(
            String orderType, String orderStatus,
            UUID customerId, UUID managerId, UUID orderedCarId) {
        Specification<OrderEntity> spec = Specification.where(null);

        if (orderType != null) spec = spec.and(hasOrderType(orderType));
        if (orderStatus != null) spec = spec.and(hasOrderStatus(orderStatus));

        if (customerId != null) spec = spec.and(hasCustomerId(customerId));
        if (managerId != null) spec = spec.and(hasManagerId(managerId));
        if (orderedCarId != null) spec = spec.and(hasOrderedCarId(orderedCarId));

        return spec;
    }
}
