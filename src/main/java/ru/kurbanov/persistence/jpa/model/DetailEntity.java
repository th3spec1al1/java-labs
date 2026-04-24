package ru.kurbanov.persistence.jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.kurbanov.persistence.jpa.listeners.BaseEntityListener;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@EntityListeners(BaseEntityListener.class)
@Table(name = "details")
public class DetailEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "compatible_models")
    private List<String> compatibleModels;
}
