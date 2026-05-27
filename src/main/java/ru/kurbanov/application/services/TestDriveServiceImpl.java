package ru.kurbanov.application.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kurbanov.application.abstractions.repositories.jpa.JpaCarRepository;
import ru.kurbanov.application.abstractions.repositories.jpa.JpaTestDriveRepository;
import ru.kurbanov.application.contracts.TestDriveService;
import ru.kurbanov.domain.entities.cars.Car;
import ru.kurbanov.domain.entities.details.Detail;
import ru.kurbanov.domain.entities.testdrives.TestDrive;
import ru.kurbanov.domain.entities.users.Customer;
import ru.kurbanov.infrastructure.persistence.jpa.model.CarEntity;
import ru.kurbanov.infrastructure.persistence.jpa.model.TestDriveEntity;
import ru.kurbanov.infrastructure.persistence.mappers.CarEntityMapper;
import ru.kurbanov.infrastructure.persistence.mappers.TestDriveEntityMapper;
import ru.kurbanov.presentation.dto.requests.TestDriveRequestDto;
import ru.kurbanov.presentation.dto.responses.TestDriveResponseDto;
import ru.kurbanov.presentation.mappers.TestDriveDtoMapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TestDriveServiceImpl implements TestDriveService {

    private final JpaTestDriveRepository testDriveRepository;
    private final JpaCarRepository carRepository;
    private final TestDriveEntityMapper testDriveEntityMapper;
    private final TestDriveDtoMapper testDriveDtoMapper;
    private final CarEntityMapper carEntityMapper;
    private final CarDetailsLoader carDetailsLoader;

    private TestDriveResponseDto toDto(TestDriveEntity testDriveEntity) {
        Customer customer = new Customer(testDriveEntity.getCustomerId());
        CarEntity carEntity = carRepository.getReferenceById(testDriveEntity.getOrderedCarId());
        Map<String, Detail> details = carDetailsLoader.loadDetails(carEntity);
        Car car = carEntityMapper.toDomain(carEntity, details);

        TestDrive testDrive = testDriveEntityMapper.toDomain(testDriveEntity, customer, car);
        return testDriveDtoMapper.toDto(testDrive);
    }
    
    @Override
    public TestDriveResponseDto getTestDrive(UUID testDriveId) {
        TestDriveEntity testDriveEntity = testDriveRepository.findById(testDriveId)
                .orElseThrow(() -> new EntityNotFoundException("TestDrive not found: " + testDriveId));
        return toDto(testDriveEntity);
    }

    @Override
    public List<TestDriveResponseDto> getTestDrives() {
        return testDriveRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public TestDriveResponseDto addTestDrive(TestDriveRequestDto testDriveRequestDto) {
        Customer customer = new Customer(testDriveRequestDto.getCustomerId());
        CarEntity carEntity = carRepository.findById(testDriveRequestDto.getOrderedCarId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Car not found: " + testDriveRequestDto.getOrderedCarId()));
        Map<String, Detail> details = carDetailsLoader.loadDetails(carEntity);
        Car car = carEntityMapper.toDomain(carEntity, details);

        TestDrive testDrive = new TestDrive(customer, car, testDriveRequestDto.getStartDate());
        TestDriveEntity testDriveEntity = testDriveEntityMapper.toEntity(testDrive);
        TestDriveEntity saved = testDriveRepository.save(testDriveEntity);
        return toDto(saved);
    }

    @Override
    public TestDriveResponseDto updateTestDrive(UUID testDriveId, TestDriveRequestDto testDriveRequestDto) {
        TestDriveEntity testDriveEntity = testDriveRepository.findById(testDriveId)
                .orElseThrow(() -> new EntityNotFoundException("TestDrive not found: " + testDriveId));

        testDriveEntity.setCustomerId(testDriveRequestDto.getCustomerId());
        testDriveEntity.setOrderedCarId(testDriveRequestDto.getOrderedCarId());
        testDriveEntity.setStartDate(testDriveRequestDto.getStartDate());

        return toDto(testDriveEntity);
    }

    @Override
    public void removeTestDrive(UUID testDriveId) {
        testDriveRepository.deleteById(testDriveId);
    }
}
