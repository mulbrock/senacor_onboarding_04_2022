package org.acme.controllers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PersonControllerTest {

    @Test
    public void testEmptyPersonsEndpoint(){
        given()
                .when().get("/persons")
                .then()
                .statusCode(200)
                .body(is("[]"));
    }

    @Test
    public void testCreatePersonEndpoint(){

    }

}
