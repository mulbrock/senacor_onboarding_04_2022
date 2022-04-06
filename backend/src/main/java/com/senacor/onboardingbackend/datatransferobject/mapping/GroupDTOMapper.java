package com.senacor.onboardingbackend.datatransferobject.mapping;

import com.senacor.onboardingbackend.datatransferobject.GroupDTO;
import com.senacor.onboardingbackend.domainobject.Group;
import com.senacor.onboardingbackend.domainobject.Person;

import java.util.List;
import java.util.stream.Collectors;

public class GroupDTOMapper {

    public static List<GroupDTO.ReadGroupDTO> map(List<Group> groups) {
        return groups.stream().map(GroupDTOMapper::map).collect(Collectors.toList());
    }

    public static GroupDTO.ReadGroupDTO map(Group group) {
        if (group == null) {
            return null;
        }

        return GroupDTO.ReadGroupDTO
            .builder()
            .id(group.getId())
            .dateCreated(group.getDateCreated())
            .dateMeeting(group.getDateMeeting())
            .persons(group.getPersons().stream().map(GroupDTOMapper::map).collect(Collectors.toList()))
            .build();
    }

    private static GroupDTO.PersonInReadGroupDTO map(Person person) {
        return GroupDTO.PersonInReadGroupDTO
            .builder()
            .id(person.getId())
            .firstName(person.getFirstName())
            .lastName(person.getLastName())
            .age(person.getAge())
            .build();
    }
}
