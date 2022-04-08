package com.senacor.onboardingbackend.resource;

import com.senacor.onboardingbackend.datatransferobject.GroupDTO;
import com.senacor.onboardingbackend.domainobject.Group;
import com.senacor.onboardingbackend.exception.WasDeletedException;
import com.senacor.onboardingbackend.service.GroupService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.senacor.onboardingbackend.Fixtures.dummyGroup;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@QuarkusTest
public class GroupResourceTest {

    private static final String path = "/api/v1/groups";

    @InjectMock
    GroupService groupService;

    @Test
    public void testCreate() {
        when(groupService.create(any(GroupDTO.CreateGroupDTO.class))).thenReturn(dummyGroup());

        Map<String, Object> map = new HashMap<>();
        map.put("dateMeeting", OffsetDateTime.now().plusYears(1).toString());
        map.put("personIds", Arrays.asList(1, 2, 3));

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(map)
            .when().post(path)
            .then().statusCode(200);
    }

    @Test
    public void testCreateRandom() {
        when(groupService.createRandom()).thenReturn(dummyGroup());

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .when().post(path + "/random")
            .then().statusCode(200);
    }

    @Test
    public void testCreate_invalidDateMeeting() {
        Map<String, Object> map = new HashMap<>();
        map.put("dateMeeting", OffsetDateTime.now().minusYears(1).toString());
        map.put("personIds", Arrays.asList(1, 2, 3));

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(map)
            .when().post(path)
            .then().statusCode(400);
    }

    @Test
    public void testCreate_invalidPersonIds() {
        Map<String, Object> map = new HashMap<>();
        map.put("dateMeeting", OffsetDateTime.now().plusYears(1).toString());
        map.put("personIds", List.of());

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(map)
            .when().post(path)
            .then().statusCode(400);
    }

    @Test
    public void testAddPerson() {
        when(groupService.addPerson(anyLong(), anyLong())).thenReturn(dummyGroup());

        given()
            .accept(ContentType.JSON)
            .pathParam("id", 1)
            .pathParam("personId", 2)
            .when().put(path + "/{id}/person/{personId}")
            .then().statusCode(200);
    }

    @Test
    public void testGetAll() {
        when(groupService.getAll()).thenReturn(List.of(dummyGroup(), dummyGroup()));

        given()
            .accept(ContentType.JSON)
            .when().get(path)
            .then().statusCode(200);
    }

    @Test
    public void testGetById() {
        long id = 132;
        Group group = dummyGroup();
        group.setId(id);

        when(groupService.get(eq(id))).thenReturn(group);

        given()
            .accept(ContentType.JSON)
            .pathParam("id", id)
            .when().get(path + "/{id}")
            .then().statusCode(200)
            .body("id", equalTo((int) id));
    }

    @Test
    public void testDeleteById() {
        long id = 132;

        given()
            .accept(ContentType.JSON)
            .pathParam("id", id)
            .when().delete(path + "/{id}")
            .then().statusCode(204);
    }

    @Test
    public void testDeleteById_alreadyDeleted() {
        long id = 132;

        when(groupService.get(eq(id))).thenThrow(new WasDeletedException("bla bla"));
        doCallRealMethod().when(groupService).deleteById(eq(id));

        given()
            .accept(ContentType.JSON)
            .pathParam("id", id)
            .when().delete(path + "/{id}")
            .then().statusCode(204);
    }

}