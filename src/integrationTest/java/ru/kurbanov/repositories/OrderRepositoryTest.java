package ru.kurbanov.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kurbanov.TestCarDealershipApplication;
import ru.kurbanov.application.abstractions.repositories.jpa.JpaOrderRepository;
import ru.kurbanov.infrastructure.persistence.specifications.OrderSpecifications;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderRepositoryTest extends TestCarDealershipApplication {

    @Autowired
    private JpaOrderRepository orderRepository;

    @Test
    void shouldFinalAllOrders() {
        var orders = orderRepository.findAll();
        assertThat(orders).isNotEmpty();
    }

    @Test
    void shouldFindByOrderType() {
        var orders = orderRepository.findAll(OrderSpecifications.hasOrderType("AVAILABLE"));
        assertThat(orders).allMatch(c -> "AVAILABLE".equals(c.getOrderType()));
    }
}
