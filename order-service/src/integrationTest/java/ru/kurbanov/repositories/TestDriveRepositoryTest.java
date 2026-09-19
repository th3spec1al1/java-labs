package ru.kurbanov.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kurbanov.TestCarDealershipApplication;
import ru.kurbanov.application.abstractions.repositories.jpa.JpaTestDriveRepository;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestDriveRepositoryTest extends TestCarDealershipApplication {

    @Autowired
    private JpaTestDriveRepository testDriveRepository;

    @Test
    void shouldFinalAllTestDrives() {
        var testDrives = testDriveRepository.findAll();
        assertThat(testDrives).isNotEmpty();
    }
}
