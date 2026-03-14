package com.autoflex;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class ProductionResourceTest {

    @Test
    void shouldReturnProductionSuggestion() {
        given()
          .when().get("/api/production/suggestion")
          .then()
             .statusCode(200)
             .body("items", notNullValue())
             .body("totalValue", notNullValue());
    }
}