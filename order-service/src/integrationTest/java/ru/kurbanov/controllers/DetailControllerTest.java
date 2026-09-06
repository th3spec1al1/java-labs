package ru.kurbanov.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.kurbanov.TestCarDealershipApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kurbanov.application.services.CarGrpcClient;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DetailControllerTest extends TestCarDealershipApplication {

    @MockBean
    private CarGrpcClient carGrpcClient;

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void setUp() {
        RestAssured.port = serverPort;
    }

    @Test
    void getAllDetailsShouldReturn200() {
        given().
                contentType(ContentType.JSON)
                .when()
                .get("/details")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    void getDetailByIdShouldReturn200() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/details/a1111111-1111-1111-1111-111111111111")
                .then()
                .statusCode(200)
                .body("name", equalTo("17'' Standard"));
    }

    @Test
    void getMissingDetailShouldReturn404() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/details/00000000-0000-0000-0000-000000000000")
                .then()
                .statusCode(404);
    }

    @Test
    void createDetailShouldReturn201() {
        String body = """
        {
          "type": "INTERIOR",
          "name": "Leather Black",
          "price": 50000,
          "compatibleModels": ["BMW 320i", "BMW 330i"]
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/details")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("Leather Black"))
                .body("type", equalTo("INTERIOR"));
    }
}
