package org.acme.controllers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.data.DummyDataCreator;
import org.acme.data.services.GroupService;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class GroupControllerTest {
    private static final String path = "groups";

    @Inject
    GroupService groupService;

    @Test
    public void testCreate(){
        Map<String, Object> groupCreateSettings = new HashMap<>();
        groupCreateSettings.put("meetingTime", DummyDataCreator.groupMeetingTime);
        groupCreateSettings.put("memberIDs", new ArrayList<>().add(1L));

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(groupCreateSettings)
                .when()
                .post(path + "/create")
                .then()
                .statusCode(201);
    }
}
