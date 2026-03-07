package ru.kurbanov.repositories.inmemory;

import ru.kurbanov.domain.entities.cars.TestDrive;
import ru.kurbanov.repositories.TestDriveRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryTestDriveRepository implements TestDriveRepository {

    private final Map<UUID, TestDrive> testDrives = new HashMap<>();

    @Override
    public TestDrive save(TestDrive testDrive) {
        return testDrives.put(testDrive.getId(), testDrive);
    }

    @Override
    public void delete(TestDrive testDrive) {
        testDrives.remove(testDrive.getId());
    }

    @Override
    public TestDrive findById(UUID id) {
        return testDrives.get(id);
    }

    @Override
    public Collection<TestDrive> show() {
        return testDrives.values();
    }
}
