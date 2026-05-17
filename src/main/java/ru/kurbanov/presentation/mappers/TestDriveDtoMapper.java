package ru.kurbanov.presentation.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kurbanov.domain.entities.testdrives.TestDrive;
import ru.kurbanov.presentation.dto.requests.TestDriveRequestDto;
import ru.kurbanov.presentation.dto.responses.TestDriveResponseDto;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TestDriveDtoMapper {

    TestDriveResponseDto toDto(TestDrive testDrive);

    @Mapping(target = "id", source = "id")
    TestDrive toDomain(UUID id, TestDriveRequestDto testDriveRequestDto);
}
