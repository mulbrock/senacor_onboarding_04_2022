package net.bmw.resource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import net.bmw.TestContainersDbInitializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(TestContainersDbInitializer.class)
class PersonResourceTest {

    @Test
    void getPerson() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getAllGroupsOfPerson() {
    }

    @Test
    void createPerson() {
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