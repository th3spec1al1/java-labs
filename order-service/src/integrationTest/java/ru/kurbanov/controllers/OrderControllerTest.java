package ru.kurbanov.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.support.TransactionTemplate;
import ru.kurbanov.TestCarDealershipApplication;
import ru.kurbanov.application.abstractions.repositories.jpa.JpaOrderRepository;
import ru.kurbanov.application.abstractions.repositories.jpa.JpaOutboxEventRepository;
import ru.kurbanov.config.TestSecurityConfig;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kurbanov.application.services.CarGrpcClient;
import ru.kurbanov.infrastructure.persistence.jpa.model.OrderEntity;
import ru.kurbanov.infrastructure.persistence.jpa.model.OutboxEventEntity;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestSecurityConfig.class)
public class OrderControllerTest extends TestCarDealershipApplication {

    @MockBean
    private CarGrpcClient carGrpcClient;

    @Autowired
    private JpaOutboxEventRepository outboxEventRepository;

    @Autowired
    private JpaOrderRepository orderRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void setUp() {
        RestAssured.port = serverPort;
    }

    @Test
    void getMissingOrderShouldReturn404() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/orders/00000000-0000-0000-0000-000000000000")
                .then()
                .statusCode(404);
    }

    @Test
    void getOrdersByTypeShouldReturn200() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"orderType\": \"AVAILABLE\"}")
                .when()
                .post("/orders/filter")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("orderType", everyItem(equalTo("AVAILABLE")));
    }

    @Test
    void getOrderByIdShouldReturn200() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/orders/f1111111-1111-1111-1111-111111111111")
                .then()
                .statusCode(200)
                .body("orderType", equalTo("AVAILABLE"))
                .body("orderStatus", equalTo("CREATED"));
    }

    @Test
    void createOrderShouldReturn201() {
        String body = """
        {
          "orderType": "AVAILABLE",
          "orderedCarId": "e1111111-1111-1111-1111-111111111111"
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/orders")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("orderType", equalTo("AVAILABLE"));
    }

    @Test
    void payOrderTwiceShouldNotDuplicateEvents() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .patch("/orders/f1111111-1111-1111-1111-111111111111/pay")
                .then()
                .statusCode(200);

        long countAfterFirst = outboxEventRepository.findAll().stream()
                .filter(e -> e.getAggregateId().toString()
                        .equals("f1111111-1111-1111-1111-111111111111"))
                .count();

        given()
                .contentType(ContentType.JSON)
                .when()
                .patch("/orders/f1111111-1111-1111-1111-111111111111/pay")
                .then()
                .statusCode(anyOf(is(200), is(400), is(503)));

        long countAfterSecond = outboxEventRepository.findAll().stream()
                .filter(e -> e.getAggregateId().toString()
                        .equals("f1111111-1111-1111-1111-111111111111"))
                .count();

        assertEquals(countAfterFirst, countAfterSecond);
    }

    @Test
    void outboxEventShouldNotBeCreatedIfTransactionRolledBack() {
        long countBefore = outboxEventRepository.count();
        String statusBefore = orderRepository.findById(
                UUID.fromString("f1111111-1111-1111-1111-111111111111"))
                .get().getOrderStatus();

        try {
            transactionTemplate.execute(status -> {
                OrderEntity order = orderRepository.findById(
                        UUID.fromString("f1111111-1111-1111-1111-111111111111")).get();
                order.setOrderStatus("PAID");
                orderRepository.save(order);

                OutboxEventEntity event = new OutboxEventEntity();
                event.setEventId(UUID.randomUUID());
                event.setAggregateId(order.getId());
                event.setEventType("OrderSentForApproval");
                event.setPayload("{}");
                event.setAttempts(0);
                outboxEventRepository.save(event);

                status.setRollbackOnly();
                return null;
            });
        } catch (Exception ignored) {}

        String statusAfter = orderRepository.findById(
                UUID.fromString("f1111111-1111-1111-1111-111111111111"))
                .get().getOrderStatus();
        long countAfter = outboxEventRepository.count();

        assertEquals(statusBefore, statusAfter);
        assertEquals(countBefore, countAfter);
    }
}
