package com.senacor.onboardingbackend.service;

import com.senacor.onboardingbackend.PostgresDatabaseTest;
import com.senacor.onboardingbackend.datatransferobject.PersonDTO;
import com.senacor.onboardingbackend.domainobject.Group;
import com.senacor.onboardingbackend.domainobject.Person;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

import static com.senacor.onboardingbackend.Fixtures.existsInGroup;

@QuarkusTest
public class PersonServiceDatabaseTest extends PostgresDatabaseTest {

    @Inject
    PersonService personService;

    @Test
    public void testGet() {
        Person saved = saveRandomPerson();
        Person actual = personService.get(saved.getId());
        Assertions.assertEquals(saved, actual);
    }

    @Test
    public void testGetAll() {
        Person saved1 = saveRandomPerson();
        Person saved2 = saveRandomPerson();
        List<Person> persons = personService.getAll();
        Assertions.assertEquals(List.of(saved1, saved2), persons);
    }

    @Test
    public void testCreate() {
        var toCreate = new PersonDTO.CreateUpdatePersonDTO();
        toCreate.setFirstName("he");
        toCreate.setLastName("hu");
        toCreate.setAge(12);

        Person person = personService.create(toCreate);

        Assertions.assertNotNull(person.getId());
        Assertions.assertEquals(toCreate.getFirstName(), person.getFirstName());
        Assertions.assertEquals(toCreate.getLastName(), person.getLastName());
        Assertions.assertEquals(toCreate.getAge(), person.getAge());
    }

    @Test
    public void testUpdateById() {
        Person saved = saveRandomPerson();

        var toUpdate = new PersonDTO.CreateUpdatePersonDTO();
        toUpdate.setFirstName("updatedFirst");
        toUpdate.setLastName("updatedLast");
        toUpdate.setAge(2);

        Person updated = personService.updateById(saved.getId(), toUpdate);

        Assertions.assertEquals(saved.getId(), updated.getId());
        Assertions.assertEquals(toUpdate.getFirstName(), updated.getFirstName());
        Assertions.assertEquals(toUpdate.getLastName(), updated.getLastName());
        Assertions.assertEquals(toUpdate.getAge(), updated.getAge());
    }

    @Test
    public void testDeleteById() {
        Person saved = saveRandomPerson();
        Assertions.assertFalse(saved.isDeleted());

        personService.deleteById(saved.getId());

        Person deleted = findPerson(saved.getId());
        Assertions.assertTrue(deleted.isDeleted());
    }

    @Test
    public void testAddToGroup() {
        Person person1 = saveRandomPerson();
        Person person2 = saveRandomPerson();
        Group group = saveRandomGroup();
        Set<Long> ids = Set.of(person1.getId(), person2.getId());

        addToGroupInTx(ids, group);

        {
            Group actualGroup = findGroup(group.getId());
            Assertions.assertEquals(2, actualGroup.getPersons().size());
            Assertions.assertTrue(existsInGroup(group, person1.getId()));
            Assertions.assertTrue(existsInGroup(group, person2.getId()));
        }

        {
            Person actualPerson1 = findPerson(person1.getId());
            Assertions.assertEquals(1, actualPerson1.getGroups().size());
            Assertions.assertTrue(existsInGroup(actualPerson1, group.getId()));
        }

        {
            Person actualPerson2 = findPerson(person2.getId());
            Assertions.assertEquals(1, actualPerson2.getGroups().size());
            Assertions.assertTrue(existsInGroup(actualPerson2, group.getId()));
        }
    }

    @Transactional
    public void addToGroupInTx(Set<Long> personIds, Group group) {
        personService.addToGroup(personIds, group);
    }

}
