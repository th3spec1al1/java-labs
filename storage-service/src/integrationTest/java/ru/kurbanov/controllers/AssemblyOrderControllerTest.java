package ru.kurbanov.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.kurbanov.TestStorageServiceApplication;
import ru.kurbanov.config.TestSecurityConfig;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class AssemblyOrderControllerTest extends TestStorageServiceApplication {

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void setUp() {
        RestAssured.port = serverPort;
    }

    @Test
    void getAllShouldReturn200() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/assembly-orders")
                .then()
                .statusCode(200);
    }
}
