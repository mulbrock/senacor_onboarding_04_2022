package org.acme.data.services;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.acme.controllers.transfer.GroupTransferObject;
import org.acme.controllers.transfer.PersonTransferObject;
import org.acme.data.DummyDataCreator;
import org.acme.data.entities.Group;
import org.acme.data.entities.Person;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@QuarkusTest
public class GroupServiceTest {

    @InjectMock
    Session session;

    private GroupService groupService = Mockito.mock(GroupService.class);
    private PersonService personService = Mockito.mock(PersonService.class);

    @BeforeEach
    public void setup(){
        Query mockQuery = Mockito.mock(Query.class);
        Mockito.doNothing().when(session).persist(Mockito.any());
        PanacheMock.mock(Person.class);
    }

    @Test
    public void testCreate(){
        GroupTransferObject.CreateGroupDTO groupDTO = DummyDataCreator.createGroupDTO();

        Group groupToCreate = new Group();
        groupToCreate.setMeetingTime(DummyDataCreator.groupMeetingTime);
        groupToCreate.setMembers(new HashSet<>());

        Mockito.when(groupService.createGroup(Mockito.any())).thenReturn(groupToCreate);
        Assertions.assertEquals(groupToCreate, groupService.createGroup(groupDTO));
    }

    @Test
    public void testAddPersonsToGroup(){
        Person dummyPerson = DummyDataCreator.createDummyPerson();
        Group dummyGroup = DummyDataCreator.createDummyGroup();

        Assertions.assertEquals(0, dummyGroup.getMembers().size());

        Mockito.when(Person.findById(Mockito.any())).thenReturn(dummyPerson);
        Mockito.when(groupService.addPersonsToGroup(Mockito.any(), Mockito.any())).thenCallRealMethod();

        List<Long> idList = new ArrayList<>();
        idList.add(dummyPerson.id);

        groupService.addPersonsToGroup(idList, dummyGroup);

        Assertions.assertEquals(1, dummyGroup.getMembers().size());
        Assertions.assertTrue(dummyGroup.getMembers().contains(dummyPerson));
    }


}
