package com.senacor.onboardingbackend.service;

import com.senacor.onboardingbackend.datatransferobject.PersonDTO;
import com.senacor.onboardingbackend.domainobject.Person;
import com.senacor.onboardingbackend.exception.NotFoundException;
import com.senacor.onboardingbackend.exception.WasDeletedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static com.senacor.onboardingbackend.Fixtures.dummyPerson;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    private EntityManager em = Mockito.mock(EntityManager.class);
    private PersonService personService = new PersonService(em);

    @Test
    public void testGet_notFound() {
        when(em.find(any(), any())).thenReturn(null);

        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> personService.get(2L));

        Assertions.assertEquals("Cannot find Person with id=2", thrown.getMessage());
    }

    @Test
    public void testGet_wasDeleted() {
        Person person = new Person();
        person.setDeleted(true);

        when(em.find(any(), any())).thenReturn(person);

        WasDeletedException thrown = Assertions.assertThrows(WasDeletedException.class, () -> personService.get(2L));

        Assertions.assertEquals("Person with id=2 was deleted before", thrown.getMessage());
    }

    @Test
    public void testGet() {
        Person expected = dummyPerson();

        when(em.find(any(), any())).thenReturn(expected);

        Person actual = personService.get(2L);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() {
        Query query = Mockito.mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);

        List<Person> expected = List.of(dummyPerson(), dummyPerson());
        when(query.getResultList()).thenReturn(expected);

        List<Person> actual = personService.getAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testCreate() {
        var toCreate = new PersonDTO.CreateUpdatePersonDTO();
        toCreate.setFirstName("badabum");
        toCreate.setLastName("bombastic");
        toCreate.setAge(18);

        Person actual = personService.create(toCreate);

        Assertions.assertEquals(toCreate.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(toCreate.getLastName(), actual.getLastName());
        Assertions.assertEquals(toCreate.getAge(), actual.getAge());

        verify(em).persist(actual);
    }

    @Test
    public void testUpdateById() {
        Person expected = dummyPerson();

        var toUpdate = new PersonDTO.CreateUpdatePersonDTO();
        toUpdate.setFirstName("badabum");
        toUpdate.setLastName("bombastic");
        toUpdate.setAge(18888);

        when(em.find(any(), any())).thenReturn(expected);
        when(em.merge(any())).thenReturn(expected);

        Person actual = personService.updateById(expected.getId(), toUpdate);

        Assertions.assertEquals(toUpdate.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(toUpdate.getLastName(), actual.getLastName());
        Assertions.assertEquals(toUpdate.getAge(), actual.getAge());

        verify(em).merge(actual);
    }


    @Test
    public void testDeleteById() {
        Person expected = dummyPerson();
        expected.setDeleted(false);

        when(em.find(any(), eq(expected.getId()))).thenReturn(expected);

        personService.deleteById(expected.getId());

        Assertions.assertTrue(expected.isDeleted());
        verify(em).merge(expected);
    }

    @Test
    public void testDeleteById_alreadyDeleted() {
        Person expected = dummyPerson();
        expected.setDeleted(true);

        when(em.find(any(), eq(expected.getId()))).thenReturn(expected);

        personService.deleteById(expected.getId());

        Assertions.assertTrue(expected.isDeleted());
        verify(em, never()).merge(any());
    }

}
