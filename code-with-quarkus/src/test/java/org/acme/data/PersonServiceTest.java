package org.acme.data;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.controllers.transfer.PersonTransferObject;
import org.acme.data.entities.Person;
import org.acme.data.services.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

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
}
