package org.acme.data;

import org.acme.controllers.transfer.GroupTransferObject;
import org.acme.controllers.transfer.PersonTransferObject;
import org.acme.data.entities.Group;
import org.acme.data.entities.Person;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DummyDataCreator {

    public static final LocalDateTime groupMeetingTime = LocalDateTime.parse("2007-12-03T10:15:30");

    public static PersonTransferObject.CreateUpdatePersonDTO createPersonDTO() {
        PersonTransferObject.CreateUpdatePersonDTO personDTO =
                new PersonTransferObject.CreateUpdatePersonDTO();
        personDTO.setAge(22);
        personDTO.setFirstName("Hans");
        personDTO.setLastName("Strange");

        return personDTO;
    }

    public static Person createDummyPerson() {

        Person person = new Person();
        person.id = 12L;
        person.setFirstName("Frida");
        person.setLastName("Stralau");
        person.setAge(32);

        return person;
    }

    public static GroupTransferObject.CreateGroupDTO createGroupDTO() {
        GroupTransferObject.CreateGroupDTO groupDTO = new GroupTransferObject.CreateGroupDTO();
        groupDTO.setMeetingTime(groupMeetingTime);

        List<Long> memberIDs = new ArrayList<>();
        groupDTO.setMemberIDs(memberIDs);
        return groupDTO;
    }

    public static Group createDummyGroup() {
        Group group = new Group();
        group.setMeetingTime(groupMeetingTime);
        group.setMembers(new HashSet<>());

        return group;
    }

}
