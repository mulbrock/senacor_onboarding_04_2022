package com.senacor.onboardingbackend.service;

import com.senacor.onboardingbackend.datatransferobject.GroupDTO;
import com.senacor.onboardingbackend.domainobject.Group;
import com.senacor.onboardingbackend.exception.NotFoundException;
import com.senacor.onboardingbackend.exception.WasDeletedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import static com.senacor.onboardingbackend.Fixtures.dummyGroup;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class GroupServiceTest {

    private EntityManager em = Mockito.mock(EntityManager.class);
    private PersonService personService = Mockito.mock(PersonService.class);
    private GroupService groupService = new GroupService(em, personService);

    @Test
    public void testGet_notFound() {
        when(em.find(any(), any())).thenReturn(null);

        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> groupService.get(2L));

        Assertions.assertEquals("Cannot find Group with id=2", thrown.getMessage());
    }

    @Test
    public void testGet_wasDeleted() {
        Group group = new Group();
        group.setDeleted(true);

        when(em.find(any(), any())).thenReturn(group);

        WasDeletedException thrown = Assertions.assertThrows(WasDeletedException.class, () -> groupService.get(2L));

        Assertions.assertEquals("Group with id=2 was deleted before", thrown.getMessage());
    }

    @Test
    public void testGet() {
        Group expected = dummyGroup();

        when(em.find(any(), any())).thenReturn(expected);

        Group actual = groupService.get(2L);

        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void testGetAll() {
        Query query = Mockito.mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);

        List<Group> expected = List.of(dummyGroup(), dummyGroup());
        when(query.getResultList()).thenReturn(expected);

        List<Group> actual = groupService.getAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testCreate() {
        var toCreate = new GroupDTO.CreateGroupDTO();
        toCreate.setDateMeeting(OffsetDateTime.now().plusYears(1));
        toCreate.setPersonIds(Set.of(1L, 2L));

        Group actual = groupService.create(toCreate);

        Assertions.assertNotNull(actual.getDateCreated());
        Assertions.assertEquals(toCreate.getDateMeeting(), actual.getDateMeeting());

        verify(em).persist(actual);
        verify(personService).addToGroup(toCreate.getPersonIds(), actual);
    }

    @Test
    public void testAddPerson() {
        Group expected = dummyGroup();
        when(em.find(any(), eq(expected.getId()))).thenReturn(expected);

        Group actual = groupService.addPerson(expected.getId(), 2L);

        Assertions.assertEquals(expected, actual);
        verify(personService).addToGroup(Set.of(2L), actual);
    }

    @Test
    public void testDeleteById() {
        Group expected = dummyGroup();
        expected.setDeleted(false);

        when(em.find(any(), eq(expected.getId()))).thenReturn(expected);

        groupService.deleteById(expected.getId());

        Assertions.assertTrue(expected.isDeleted());
        verify(em).merge(expected);
    }

    @Test
    public void testDeleteById_alreadyDeleted() {
        Group expected = dummyGroup();
        expected.setDeleted(true);

        when(em.find(any(), eq(expected.getId()))).thenReturn(expected);

        groupService.deleteById(expected.getId());

        Assertions.assertTrue(expected.isDeleted());
        verify(em, never()).merge(any());
    }

}
