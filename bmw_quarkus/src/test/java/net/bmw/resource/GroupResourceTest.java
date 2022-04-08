package net.bmw.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import net.bmw.model.Group;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class GroupResourceIntegrationTest {

    @Test
    void whenGetAll_thenAllGroupsShouldBeListed() {
        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .when().get("/group/all")
                .then().log().ifValidationFails(LogDetail.BODY)
                .statusCode(Response.Status.OK.getStatusCode())
                .body("$", hasItems());
    }

    @Test
    void getGroup_thenSingleGroupShouldBeListed() {
        given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("id", 1)
                .when().get("/group/{id}")
                .then().log().ifValidationFails(LogDetail.BODY)
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void getAllPersonsByGroupId_thenAllPersonsOfGroupShouldBeListed() {
        given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("id", 1)
                .when().get("/group/{id}/persons")
                .then().log().ifValidationFails(LogDetail.BODY)
                .statusCode(Response.Status.OK.getStatusCode())
                .assertThat()
                .body("", hasSize(1))
                .body("id", hasItems(5))
                .body("groups", hasItems());
    }

    @Test
    void createGroup_thenGroupShouldBeCreated() {
        with().body(new Group("07.07.2022", "08.07.2022")).
        when().request("POST", "/group")
        .then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void addPerson() {
    }

    @Test
    void updateGroup() {
    }

    @Test
    void deleteGroupById() {
    }

    @Test
    void deleteAllGroups() {
    }

    @Test
    void countGroups() {
    }
}