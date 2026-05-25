package ru.kurbanov.infrastructure.persistence.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.testdrives.TestDrive;
import ru.kurbanov.domain.entities.users.Customer;
import ru.kurbanov.infrastructure.persistence.jpa.model.TestDriveEntity;

@Mapper(componentModel = "spring")
public abstract class TestDriveEntityMapper {

    @Mapping(target = "customerId", expression = "java(testDrive.getCustomer().getId())")
    @Mapping(target = "orderedCarId", expression = "java(testDrive.getCar().getId())")
    @Mapping(target = "startDate", source = "date")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "removed", ignore = true)
    public abstract TestDriveEntity toEntity(TestDrive testDrive);

    public TestDrive toDomain(TestDriveEntity entity, Customer customer, Car car) {
        if (entity == null) return null;
        return new TestDrive(
                entity.getId(),
                customer,
                car,
                entity.getStartDate()
        );
    }
}
