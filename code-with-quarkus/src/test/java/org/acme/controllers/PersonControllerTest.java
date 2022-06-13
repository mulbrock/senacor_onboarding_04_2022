package org.acme.controllers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.controllers.transfer.PersonTransferObject;
import org.acme.data.services.PersonService;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PersonControllerTest {

    @Inject
    PersonService personService;
    private static final String path = "/persons";

    @Test
    public void testEmptyPersonsEndpoint(){
        given()
                .when().get(path)
                .then()
                .statusCode(200)
                .body(is("[]"));
    }

    @Test
    public void testCreateRandomPersonsEndpoint(){

        given()
                .queryParam("amount", "5")
                .when()
                .post(path + "/create_random")
                .then()
                .statusCode(201);
    }

    @Test
    public void testCreatePerson(){


        Map<String, Object> personSettings = new HashMap<>();
        personSettings.put("firstName", "Hans");
        personSettings.put("lastName", "Strille");
        personSettings.put("age", 23);
        personSettings.put("groups", new ArrayList<>());

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(personSettings)
                .when()
                .post(path + "/new")
                .then()
                .statusCode(201);

    }

}
