package ru.kurbanov.infrastructure.persistence.mappers;

import org.mapstruct.Mapper;
import ru.kurbanov.domain.entities.testdrives.TestDrive;
import ru.kurbanov.infrastructure.persistence.jpa.model.TestDriveEntity;

@Mapper(componentModel = "spring")
public interface TestDriveEntityMapper {

    TestDriveEntity toEntity(TestDrive testDrive);
    TestDrive toDomain(TestDriveEntity testDriveEntity);
}
