package ru.kurbanov.presentation.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.testdrives.TestDrive;
import ru.kurbanov.domain.entities.users.Customer;
import ru.kurbanov.presentation.dto.requests.TestDriveRequestDto;
import ru.kurbanov.presentation.dto.responses.TestDriveResponseDto;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class TestDriveDtoMapper {

    @Mapping(target = "customerId", expression = "java(testDrive.getCustomer().getId())")
    @Mapping(target = "orderedCarId", expression = "java(testDrive.getCar().getId())")
    @Mapping(target = "startDate", source = "date")
    public abstract TestDriveResponseDto toDto(TestDrive testDrive);

    public TestDrive toDomain(UUID id, TestDriveRequestDto dto, Customer customer, Car car) {
        if (dto == null) return null;
        return new TestDrive(
                id != null ? id : UUID.randomUUID(),
                customer,
                car,
                dto.getStartDate()
        );
    }
}