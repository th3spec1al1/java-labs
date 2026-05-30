package ru.kurbanov.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.kurbanov.TestCarDealershipApplication;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest extends TestCarDealershipApplication {

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
          "orderedCarId": "e1111111-1111-1111-1111-111111111111",
          "customerId": "cccccccc-cccc-cccc-cccc-cccccccccccc"
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
                .body("orderType", equalTo("AVAILABLE"))
                .body("orderedCarId", equalTo("e1111111-1111-1111-1111-111111111111"))
                .body("customerId", equalTo("cccccccc-cccc-cccc-cccc-cccccccccccc"));
    }
}
