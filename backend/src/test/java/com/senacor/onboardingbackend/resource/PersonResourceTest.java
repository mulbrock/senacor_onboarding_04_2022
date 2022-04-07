package com.senacor.onboardingbackend.resource;

import com.senacor.onboardingbackend.datatransferobject.PersonDTO;
import com.senacor.onboardingbackend.domainobject.Person;
import com.senacor.onboardingbackend.exception.WasDeletedException;
import com.senacor.onboardingbackend.service.PersonService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.senacor.onboardingbackend.Fixtures.dummyPerson;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@QuarkusTest
public class PersonResourceTest {

    private static final String path = "/api/v1/persons";

    @InjectMock
    PersonService personService;

    @Test
    public void testCreate() {
        when(personService.create(any(PersonDTO.CreateUpdatePersonDTO.class))).thenReturn(dummyPerson());

        Map<String, Object> map = new HashMap<>();
        map.put("firstName", "bim bam bo");
        map.put("lastName", "bim bam bo");
        map.put("age", 18);

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(map)
            .when().post(path)
            .then().statusCode(200);
    }

    @Test
    public void testCreate_missingFirstName() {
        Map<String, Object> map = new HashMap<>();
        map.put("lastName", "bim bam bo");
        map.put("age", 18);

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(map)
            .when().post(path)
            .then().statusCode(400);
    }

    @Test
    public void testCreate_emptyFirstName() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", "");
        map.put("lastName", "bim bam bo");
        map.put("age", 18);

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(map)
            .when().post(path)
            .then().statusCode(400);
    }

    @Test
    public void testCreate_missingLastName() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", "bim bam bo");
        map.put("age", 18);

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(map)
            .when().post(path)
            .then().statusCode(400);
    }

    @Test
    public void testCreate_emptyLastName() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", "bim bam bo");
        map.put("lastName", "");
        map.put("age", 18);

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(map)
            .when().post(path)
            .then().statusCode(400);
    }

    @Test
    public void testCreate_missingAge() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", "bim bam bo");
        map.put("lastName", "bim bam bo");

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(map)
            .when().post(path)
            .then().statusCode(400);
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -50, -1})
    public void testCreate_invalidAge(int age) {
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", "bim bam bo");
        map.put("lastName", "bim bam bo");
        map.put("age", age);

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(map)
            .when().post(path)
            .then().statusCode(400);
    }

    @Test
    public void testGetAll() {
        when(personService.getAll()).thenReturn(List.of(dummyPerson(), dummyPerson()));

        given()
            .accept(ContentType.JSON)
            .when().get(path)
            .then().statusCode(200);
    }

    @Test
    public void testGetById() {
        long id = 24;
        Person person = dummyPerson();
        person.setId(id);

        when(personService.get(eq(id))).thenReturn(person);

        given()
            .accept(ContentType.JSON)
            .pathParam("id", id)
            .when().get(path + "/{id}")
            .then().statusCode(200)
            .body("id", equalTo((int) id));
    }

    @Test
    public void testUpdateById() {
        long id = 24;

        Map<String, Object> map = new HashMap<>();
        map.put("firstName", "heyho");
        map.put("lastName", "dabadum");
        map.put("age", 18);

        Person person = dummyPerson();
        person.setId(id);
        person.setFirstName(map.get("firstName").toString());
        person.setLastName(map.get("lastName").toString());
        person.setAge(Integer.valueOf(map.get("age").toString()));

        when(personService.updateById(anyLong(), any(PersonDTO.CreateUpdatePersonDTO.class))).thenReturn(person);

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(map)
            .pathParam("id", id)
            .when().put(path + "/{id}")
            .then().statusCode(200)
            .body("id", equalTo((int) id))
            .body("firstName", equalTo("heyho"))
            .body("lastName", equalTo("dabadum"))
            .body("age", equalTo(18));
    }

    @Test
    public void testDeleteById() {
        long id = 24;

        given()
            .accept(ContentType.JSON)
            .pathParam("id", id)
            .when().delete(path + "/{id}")
            .then().statusCode(204);
    }

    @Test
    public void testDeleteById_alreadyDeleted() {
        long id = 132;

        when(personService.get(eq(id))).thenThrow(new WasDeletedException("bla bla"));
        doCallRealMethod().when(personService).deleteById(eq(id));

        given()
            .accept(ContentType.JSON)
            .pathParam("id", id)
            .when().delete(path + "/{id}")
            .then().statusCode(204);
    }

}