package org.acme.data.services;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.controllers.transfer.GroupTransferObject;
import org.acme.controllers.transfer.PersonTransferObject;
import org.acme.data.DummyDataCreator;
import org.acme.data.PostgresDB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@QuarkusTest
@QuarkusTestResource(PostgresDB.class)
public class GroupServiceIT {

    @Inject
    GroupService groupService;

    @Inject
    PersonService personService;

    @Test
    public void testCreate() {
        PersonTransferObject.CreateUpdatePersonDTO personDTO = DummyDataCreator.createPersonDTO();
        personService.create(personDTO);

        GroupTransferObject.CreateGroupDTO groupCreateDTO = DummyDataCreator.createGroupDTO();
        List<Long> memberIDs = new ArrayList<>();
        memberIDs.add(1L);
        groupCreateDTO.setMemberIDs(memberIDs);

        groupService.createGroup(groupCreateDTO);

        Assertions.assertEquals(1, groupService.getAllGroups().size());
        GroupTransferObject.ReadGroupDTO groupReadDTO = groupService.getAllGroups().get(0);

        Assertions.assertNotNull(groupReadDTO);
        Assertions.assertEquals(groupReadDTO.getMeetingTime(), groupCreateDTO.getMeetingTime());
        Assertions.assertEquals(groupReadDTO.getGroupMembers().get(0).getId(), 1L);
    }

}
