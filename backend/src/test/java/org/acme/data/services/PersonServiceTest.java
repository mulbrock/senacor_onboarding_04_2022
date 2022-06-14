package org.acme.data.services;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.acme.controllers.transfer.PersonTransferObject;
import org.acme.data.DummyDataCreator;
import org.acme.data.entities.Person;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.Query;

@QuarkusTest
public class PersonServiceTest {

    @InjectMock
    Session session;

    private PersonService personService = Mockito.mock(PersonService.class);

    @BeforeEach
    public void setup(){
        Query mockQuery = Mockito.mock(Query.class);
        Mockito.doNothing().when(session).persist(Mockito.any());
    }


    @Test
    public void testCreate(){
        PersonTransferObject.CreateUpdatePersonDTO personToCreate = DummyDataCreator.createPersonDTO();

        Mockito.when(personService.create(personToCreate)).thenReturn(true);
        Assertions.assertTrue(personService.create(personToCreate));
    }

    @Test
    public void testUpdateByID(){
        PanacheMock.mock(Person.class);

        long id = 12L;
        Person dummyPerson = DummyDataCreator.createDummyPerson();

        PersonTransferObject.CreateUpdatePersonDTO personUpdates = new PersonTransferObject.CreateUpdatePersonDTO();
        personUpdates.setFirstName("FIRST");
        personUpdates.setLastName("LAST");
        personUpdates.setAge(12);

        Mockito.when(Person.findById(id)).thenReturn(dummyPerson);
        Mockito.doNothing().when(session).persist(Mockito.any());
        Mockito.when(personService.updateByID(id, personUpdates)).thenCallRealMethod();
        Mockito.when(personService.populateDataFromDTO(dummyPerson, personUpdates)).thenCallRealMethod();

        personService.updateByID(id, personUpdates);

        Assertions.assertEquals(personUpdates.getFirstName(), dummyPerson.getFirstName());
        Assertions.assertEquals(personUpdates.getLastName(), dummyPerson.getLastName());
        Assertions.assertEquals(personUpdates.getAge(), dummyPerson.getAge());
    }

    @Test
    public void testDeleteByID(){
        long id = 12L;
        Mockito.when(personService.deleteByID(id)).thenReturn(true);
        Assertions.assertTrue(personService.deleteByID(id));
    }

    @Test
    public void testPopulateDataFromDTO(){
        PersonTransferObject.CreateUpdatePersonDTO personDTO = new PersonTransferObject.CreateUpdatePersonDTO();
        Person person = new Person();
        personService.populateDataFromDTO(person, personDTO);

        Assertions.assertEquals(person.getFirstName(), personDTO.getFirstName());
        Assertions.assertEquals(person.getLastName(), personDTO.getLastName());
        Assertions.assertEquals(person.getAge(), personDTO.getAge());
    }



}
