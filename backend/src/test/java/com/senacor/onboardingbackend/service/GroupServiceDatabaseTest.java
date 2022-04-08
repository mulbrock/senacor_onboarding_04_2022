package com.senacor.onboardingbackend.service;

import com.senacor.onboardingbackend.PostgresDatabaseTest;
import com.senacor.onboardingbackend.datatransferobject.GroupDTO;
import com.senacor.onboardingbackend.domainobject.Group;
import com.senacor.onboardingbackend.domainobject.Person;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import static com.senacor.onboardingbackend.Fixtures.existsInGroup;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.in;

@QuarkusTest
public class GroupServiceDatabaseTest extends PostgresDatabaseTest {

    @Inject
    GroupService groupService;

    @Test
    public void testGet() {
        Group saved = saveRandomGroup();
        Group actual = groupService.get(saved.getId());
        Assertions.assertEquals(saved, actual);
    }

    @Test
    public void testGetAll() {
        Group saved1 = saveRandomGroup();
        Group saved2 = saveRandomGroup();
        List<Group> persons = groupService.getAll();
        Assertions.assertEquals(List.of(saved1, saved2), persons);
    }

    @Test
    public void testCreate() {
        Person saved1 = saveRandomPerson();
        Person saved2 = saveRandomPerson();

        var toCreate = new GroupDTO.CreateGroupDTO();
        toCreate.setDateMeeting(OffsetDateTime.now().plusMonths(1));
        toCreate.setPersonIds(Set.of(saved1.getId(), saved2.getId()));

        Group fromJava = groupService.create(toCreate);
        Group fromDB = findGroup(fromJava.getId());

        Assertions.assertNotNull(fromDB.getId());
        Assertions.assertEquals(2, fromDB.getPersons().size());
        Assertions.assertTrue(existsInGroup(fromDB, saved1.getId()));
        Assertions.assertTrue(existsInGroup(fromDB, saved2.getId()));
    }

    @Test
    public void testCreateRandom() {
        Person p1 = saveRandomPerson();
        Person p2 = saveRandomPerson();
        Person p3 = saveRandomPerson();
        Person p4 = saveRandomPerson();
        Set<Person> allPersons = Set.of(p1, p2, p3, p4);

        Group fromJava = groupService.createRandom();
        Group fromDB = findGroup(fromJava.getId());

        Assertions.assertNotNull(fromDB.getId());
        Assertions.assertNotNull(fromDB.getDateCreated());
        Assertions.assertNotNull(fromDB.getDateMeeting());
        Assertions.assertNotEquals(0, fromDB.getPersons().size());

        assertThat(fromDB.getPersons(), everyItem(in(allPersons)));
    }

    @Test
    public void testAddPerson() {
        Group group = saveRandomGroup();
        Person person = saveRandomPerson();

        Group fromJava = groupService.addPerson(group.getId(), person.getId());
        Group fromDB = findGroup(fromJava.getId());

        Assertions.assertEquals(1, fromDB.getPersons().size());
        Assertions.assertTrue(existsInGroup(fromDB, person.getId()));
    }


    @Test
    public void testDeleteById() {
        Group group = saveRandomGroup();
        Assertions.assertFalse(group.isDeleted());

        groupService.deleteById(group.getId());

        Group deleted = findGroup(group.getId());
        Assertions.assertTrue(deleted.isDeleted());
    }
}
