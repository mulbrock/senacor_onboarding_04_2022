package net.bmw.resource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import net.bmw.TestContainersDbInitializer;
import net.bmw.model.Group;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@QuarkusTestResource(TestContainersDbInitializer.class)
class GroupResourceIntegrationTest {

    @Test
    @Order(2)
    void whenGetAll_thenAllGroupsShouldBeListed() {
        given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .when().get("/group/all")
                .then().log().ifValidationFails(LogDetail.BODY)
                .statusCode(Response.Status.NOT_FOUND.getStatusCode())
                .body("$", hasItems());
    }

    @Test
    @Order(3)
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
    @Order(1)
    void createGroup_thenGroupShouldBeCreated() {
        with().body(new Group("07.07.2022", "08.07.2022")).contentType(ContentType.JSON).accept(ContentType.JSON)
        .when().request("POST", "/group")
        .then().statusCode(Response.Status.OK.getStatusCode());
    }

//    @Test
//    void addPerson_thenTheGroupShouldHavePerson() {
//        with().body().contentType(ContentType.JSON).accept(ContentType.JSON)
//                .when().request("POST", )
//    }

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