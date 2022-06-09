package org.acme.controllers.mapper;

import org.acme.controllers.transfer.GroupTransferObject;
import org.acme.data.entities.Group;
import org.acme.data.entities.Person;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {

    public static List<GroupTransferObject.ReadGroupDTO> map(List<Group> groups){
        return groups.stream().map(GroupMapper::map).collect(Collectors.toList());
    }

    public static GroupTransferObject.ReadGroupDTO map(Group group){
        if (group == null){
            return null;
        }

        return GroupTransferObject.ReadGroupDTO
                .builder()
                .id(group.id)
                .creationTime(group.getCreationTime())
                .meetingTime(group.meetingTime)
                .groupMembers(group.getMembers().stream()
                        .map(GroupMapper::mapToMemberInGroupDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    private static GroupTransferObject.MemberInGroupDTO mapToMemberInGroupDTO(Person person){
        return GroupTransferObject.MemberInGroupDTO
                .builder()
                .id(person.id)
                .firstName(person.firstName)
                .lastName(person.lastName)
                .age(person.age)
                .build();
    }

}
