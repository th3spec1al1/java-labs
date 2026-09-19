package ru.kurbanov.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kurbanov.TestCarDealershipApplication;
import ru.kurbanov.application.abstractions.repositories.jpa.JpaDetailRepository;
import ru.kurbanov.infrastructure.persistence.jpa.model.DetailEntity;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DetailRepositoryTest extends TestCarDealershipApplication {

    @Autowired
    private JpaDetailRepository detailRepository;

    @Test
    void shouldFindAllDetails() {
        var details = detailRepository.findAll();
        assertThat(details).isNotEmpty();
    }

    @Test
    void shouldFindByType() {
        var details = detailRepository.findAll();
        assertThat(details)
                .extracting(DetailEntity::getType)
                .contains("WHEELS", "TRANSMISSION", "STEERING_WHEEL", "INTERIOR");
    }
}
