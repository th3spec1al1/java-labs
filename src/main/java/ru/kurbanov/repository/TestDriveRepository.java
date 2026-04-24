package ru.kurbanov.repository;

import ru.kurbanov.domain.entities.testdrives.TestDrive;

import java.util.Collection;
import java.util.UUID;

public interface TestDriveRepository {

    TestDrive save(TestDrive testDrive);
    void delete(TestDrive testDrive);
    TestDrive findById(UUID id);
    Collection<TestDrive> show();
}
