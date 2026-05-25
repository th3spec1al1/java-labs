package ru.kurbanov.infrastructure.persistence.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.kurbanov.domain.entities.cars.enums.BodyType;
import ru.kurbanov.infrastructure.persistence.jpa.model.CarEntity;

import java.math.BigDecimal;
import java.util.UUID;

public class CarSpecifications {

    public static Specification<CarEntity> hasBrand(String brand) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("brand"), brand));
    }

    public static Specification<CarEntity> hasModel(String model) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("model"), model));
    }

    public static Specification<CarEntity> hasFuelType(String fuelType) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("fuelType"), fuelType));
    }

    public static Specification<CarEntity> hasCarBody(BodyType carBody) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("carBody"), carBody));
    }

    public static Specification<CarEntity> hasCarDrive(String carDrive) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("carDrive"), carDrive));
    }

    public static Specification<CarEntity> hasGearboxType(String gearboxType) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("gearboxType"), gearboxType));
    }

    public static Specification<CarEntity> hasColor(String color) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("color"), color));
    }

    public static Specification<CarEntity> priceLessThanOrEqual(BigDecimal maxPrice) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
    }

    public static Specification<CarEntity> enginePowerGreaterThanOrEqual(Integer minPower) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("enginePower"), minPower));
    }

    public static Specification<CarEntity> engineDisplacementGreaterThanOrEqual(Integer minDisplacement) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("engineDisplacement"), minDisplacement));
    }

    public static Specification<CarEntity> hasDetail(UUID detailId) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("interiorId"), detailId),
                        criteriaBuilder.equal(root.get("steeringWheelId"), detailId),
                        criteriaBuilder.equal(root.get("transmissionId"), detailId),
                        criteriaBuilder.equal(root.get("wheelsId"), detailId)));
    }

    public static Specification<CarEntity> buildFilter(
            String brand, String model, String fuelType,
            BodyType carBody, String carDrive, String gearboxType, String color,
            BigDecimal maxPrice, Integer minPower, Integer minDisplacement,
            UUID interiorId, UUID steeringWheel, UUID transmissionId, UUID wheelsId) {
        Specification<CarEntity> spec = Specification.where(null);

        if (brand != null) spec = spec.and(hasBrand(brand));
        if (brand != null && model != null) spec = spec.and(hasModel(model));

        if (fuelType != null) spec = spec.and(hasFuelType((fuelType)));
        if (carBody != null) spec = spec.and(hasCarBody(carBody));
        if (carDrive != null) spec = spec.and(hasCarDrive(carDrive));
        if (gearboxType != null) spec = spec.and(hasGearboxType(gearboxType));
        if (color != null) spec = spec.and(hasColor(color));
        if (maxPrice != null) spec = spec.and(priceLessThanOrEqual(maxPrice));
        if (minPower != null) spec = spec.and(enginePowerGreaterThanOrEqual(minPower));
        if (minDisplacement != null) spec = spec.and(engineDisplacementGreaterThanOrEqual(minDisplacement));

        if (interiorId != null) spec = spec.and(hasDetail(interiorId));
        if (steeringWheel != null) spec = spec.and(hasDetail(steeringWheel));
        if (transmissionId != null) spec = spec.and(hasDetail(transmissionId));
        if (wheelsId != null) spec = spec.and(hasDetail(wheelsId));

        return spec;
    }
}
