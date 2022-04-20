package net.bmw.resource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import net.bmw.TestContainersDbInitializer;
import net.bmw.model.Person;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@QuarkusTest
//@QuarkusTestResource(TestContainersDbInitializer.class)
class PersonResourceIntegrationTest {

//    @Test
//    void getPerson() {
//        given().get("/person/{id}").pathP
//    }

    @Test
    void getAll() {
    }

    @Test
    void getAllGroupsOfPerson() {
    }

    @Test
    @Order(1)
    void createPerson() {
        with().body(new Person("Georgi19", "Mavrov19", 19)).contentType(ContentType.JSON).accept(ContentType.JSON)
                .when().request("POST", "/person")
                .then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void assignGroupToPerson() {
    }

    @Test
    void updatePerson() {
    }

    @Test
    void deletePerson() {
    }

    @Test
    void countPersons() {
    }
}