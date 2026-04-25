package ru.kurbanov.infrastructure.persistence.jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.kurbanov.infrastructure.persistence.jpa.listeners.BaseEntityListener;

import java.util.UUID;

@Getter
@Setter
@Entity
@EntityListeners(BaseEntityListener.class)
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    @Column(name = "order_type", nullable = false)
    private String orderType;

    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "manager_id", nullable = false)
    private UUID managerId;

    @Column(name = "ordered_car_id", nullable = false)
    private UUID orderedCarId;
}
