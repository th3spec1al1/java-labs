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
public class CarControllerTest extends TestCarDealershipApplication {

    @MockBean
    private CarGrpcClient carGrpcClient;

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void setUp() {
        RestAssured.port = serverPort;
    }

    @Test
    void getMissingCarShouldReturn404() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/cars/00000000-0000-0000-0000-000000000000")
                .then()
                .statusCode(404);
    }

    @Test
    void getCarsByBrandShouldReturn200() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"brand\": \"BMW\"}")
                .when()
                .post("/cars/filter")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("brand", everyItem(equalTo("BMW")));
    }

    @Test
    void getCarByIdShouldReturn200() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/cars/e1111111-1111-1111-1111-111111111111")
                .then()
                .statusCode(200)
                .body("brand", equalTo("BMW"))
                .body("model", equalTo("320i"));
    }

    @Test
    void createCarShouldReturn201() {
        String body = """
        {
          "brand": "BMW",
          "model": "320i",
          "fuelType": "PETROL",
          "carBody": "SEDAN",
          "carDrive": "REAR",
          "gearboxType": "AUTOMATIC",
          "color": "BLACK",
          "enginePower": 184,
          "engineDisplacement": 2000,
          "price": 3500000.00,
          "wheelsId": "a1111111-1111-1111-1111-111111111111",
          "transmissionId": "b1111111-1111-1111-1111-111111111111",
          "steeringWheelId": "c1111111-1111-1111-1111-111111111111",
          "interiorId": "d1111111-1111-1111-1111-111111111111"
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/cars")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("brand", equalTo("BMW"));
    }
}
