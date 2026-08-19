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
public class TestDriveControllerTest extends TestCarDealershipApplication {

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void setUp() {
        RestAssured.port = serverPort;
    }

    @Test
    void getAllTestDrivesShouldReturn200() {
        given().
                contentType(ContentType.JSON)
                .when()
                .get("/testDrives")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    void getTestDriveByIdShouldReturn200() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/testDrives/a1111111-1111-1111-1111-111111111111")
                .then()
                .statusCode(200)
                .body("customerId", equalTo("cccccccc-cccc-cccc-cccc-cccccccccccc"));
    }

    @Test
    void getMissingTestDriveShouldReturn404() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/testDrives/00000000-0000-0000-0000-000000000000")
                .then()
                .statusCode(404);
    }

    @Test
    void createTestDriveShouldReturn201() {
        String body = """
        {
          "customerId": "cccccccc-cccc-cccc-cccc-cccccccccccc",
          "orderedCarId": "e1111111-1111-1111-1111-111111111111",
          "startDate": "2026-06-01T10:00:00+03:00"
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/testDrives")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("customerId", equalTo("cccccccc-cccc-cccc-cccc-cccccccccccc"));
    }
}
