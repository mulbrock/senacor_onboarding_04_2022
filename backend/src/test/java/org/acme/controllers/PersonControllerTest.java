package org.acme.controllers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.data.DummyDataCreator;
import org.acme.data.entities.Person;
import org.acme.data.services.PersonService;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PersonControllerTest {

    @Inject
    PersonService personService;
    private static final String path = "/persons";

    @Test
    public void testEmptyPersonsEndpoint() {
        given()
                .when().get(path)
                .then()
                .statusCode(200)
                .body(is("[]"));
    }

    @Test
    public void testCreateRandomPersonsEndpoint() {

        given()
                .queryParam("amount", "5")
                .when()
                .post(path + "/create_random")
                .then()
                .statusCode(201);
    }

    @Test
    public void testCreatePerson() {

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

    @Test
    public void testUpdatePerson() {

        Map<String, Object> personUpdates = new HashMap<>();
        personUpdates.put("firstName", "Hans");
        personUpdates.put("lastName", "Strille");
        personUpdates.put("age", 23);
        personUpdates.put("groups", new ArrayList<>());

        personService.create(DummyDataCreator.createPersonDTO());
        Person person = personService.getByID(1L);

        person.setAge((Integer) personUpdates.get("age"));
        person.setFirstName(personUpdates.get("firstName").toString());
        person.setLastName(personUpdates.get("lastName").toString());

        given()
                .pathParam("id", "1")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(personUpdates)
                .when().put(path + "/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDelete() {
        personService.create(DummyDataCreator.createPersonDTO());

        given()
                .pathParam("id", "1")
                .when()
                .delete(path + "/{id}")
                .then()
                .statusCode(204);
    }

}
