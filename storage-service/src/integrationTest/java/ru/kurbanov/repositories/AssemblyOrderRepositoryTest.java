package ru.kurbanov.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.kurbanov.TestStorageServiceApplication;
import ru.kurbanov.config.TestSecurityConfig;
import ru.kurbanov.infrastructure.persistence.jpa.JpaAssemblyOrderRepository;
import ru.kurbanov.infrastructure.persistence.jpa.model.AssemblyOrderEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class AssemblyOrderRepositoryTest extends TestStorageServiceApplication {

    @Autowired
    private JpaAssemblyOrderRepository assemblyOrderRepository;

    @Test
    void shouldSaveAndFindAssemblyOrder() {
        AssemblyOrderEntity entity = new AssemblyOrderEntity();
        entity.setSourceOrderId(java.util.UUID.randomUUID());
        entity.setSourceOrderType("AVAILABLE");
        entity.setStatus("CREATED");
        entity.setRemoved(false);

        assemblyOrderRepository.save(entity);

        List<AssemblyOrderEntity> all = assemblyOrderRepository.findAll();
        assertThat(all).isNotEmpty();
    }
}
