package ru.kurbanov.repositories;

import ru.kurbanov.domain.entities.cars.TestDrive;

import java.util.Collection;
import java.util.UUID;

public interface TestDriveRepository {

    TestDrive save(TestDrive testDrive);
    void delete(TestDrive testDrive);
    TestDrive findById(UUID id);
    Collection<TestDrive> show();
}
