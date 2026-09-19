package ru.kurbanov.application.contracts;

import ru.kurbanov.presentation.dto.requests.TestDriveRequestDto;
import ru.kurbanov.presentation.dto.responses.TestDriveResponseDto;

import java.util.List;
import java.util.UUID;

public interface TestDriveService {

    TestDriveResponseDto getTestDrive(UUID testDriveId);

    List<TestDriveResponseDto> getTestDrives();

    TestDriveResponseDto addTestDrive(TestDriveRequestDto testDriveRequestDto);

    TestDriveResponseDto updateTestDrive(UUID testDriveId, TestDriveRequestDto testDriveRequestDto);

    void removeTestDrive(UUID testDriveId);
}
