package org.acme.data;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.controllers.transfer.PersonTransferObject;
import org.acme.data.entities.Person;
import org.acme.data.services.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@QuarkusTest
@QuarkusTestResource(PostgresDB.class)
public class PersonServiceTest extends DatabaseTest{

    @Inject
    PersonService personService;

    @Test
    public void testCreate(){
        PersonTransferObject.CreateUpdatePersonDTO personDTO = DummyDataCreator.createPersonDTO();
        personService.create(personDTO);

        Assertions.assertEquals(1, personService.getAllPersons().size());

        Person person = personService.getAllPersons().get(0);

        Assertions.assertNotNull(person);
        Assertions.assertEquals(22, person.getAge());
        Assertions.assertEquals("Hans", person.getFirstName());
        Assertions.assertEquals("Strange", person.getLastName());
    }

    @Test
    public void testDeleteByID(){
        PersonTransferObject.CreateUpdatePersonDTO personDTO = DummyDataCreator.createPersonDTO();
        personService.create(personDTO);

        Assertions.assertEquals(1, personService.getAllPersons().size());

        Person person = personService.getAllPersons().get(0);

        boolean success = personService.deleteByID(person.id);

        Assertions.assertTrue(success);
        Assertions.assertEquals(0, personService.getAllPersons().size());
    }

    @Test
    public void updateByID(){
        PersonTransferObject.CreateUpdatePersonDTO personDTO = DummyDataCreator.createPersonDTO();
        personService.create(personDTO);

        Assertions.assertEquals(1, personService.getAllPersons().size());

        Person person = personService.getAllPersons().get(0);

        long id = person.id;

        personDTO.setAge(33);
        personDTO.setFirstName("Hauke");
        personDTO.setLastName("Klaas");

        boolean updateSuccess = personService.updateByID(id, personDTO);
        Assertions.assertTrue(updateSuccess);

        Assertions.assertEquals(1, personService.getAllPersons().size());

        Person updatedPerson = personService.getAllPersons().get(0);

        Assertions.assertNotNull(updatedPerson);
        Assertions.assertEquals(personDTO.getFirstName(), updatedPerson.getFirstName());
        Assertions.assertEquals(personDTO.getAge(), updatedPerson.getAge());
        Assertions.assertEquals(personDTO.getLastName(), updatedPerson.getLastName());
    }

    @Test
    public void testGetAllMemberIDs(){
        PersonTransferObject.CreateUpdatePersonDTO personDTOone = DummyDataCreator.createPersonDTO();
        personService.create(personDTOone);


        PersonTransferObject.CreateUpdatePersonDTO personDTOtwo =
                new PersonTransferObject.CreateUpdatePersonDTO();
        personDTOtwo.setAge(11);
        personDTOtwo.setFirstName("Hanna");
        personDTOtwo.setLastName("Gerhardt");

        personService.create(personDTOtwo);
        Assertions.assertEquals(2, personService.getAllPersons().size());

        Person personOne = personService.getByID(1L);
        Person personTwo = personService.getByID(2L);

        Set<Long> personIDs = new HashSet<>();
        personIDs.add(personOne.id);
        personIDs.add(personTwo.id);

        Assertions.assertEquals(personIDs, personService.getAllMemberIDs());
    }

    @Test
    public void testAddToGroup(){

    }
}
