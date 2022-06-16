package org.acme.controllers.mapper;

import org.acme.controllers.transfer.PersonTransferObject;
import org.acme.data.entities.Group;
import org.acme.data.entities.Person;

import java.util.List;
import java.util.stream.Collectors;

public class PersonMapper {

    public static List<PersonTransferObject.ReadPersonDTO> map(List<Person> persons) {
        return persons.stream().map(PersonMapper::map).collect(Collectors.toList());
    }

    public static PersonTransferObject.ReadPersonDTO map(Person person) {
        if (person == null) {
            return null;
        }

        return PersonTransferObject.ReadPersonDTO
                .builder()
                .id(person.id)
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .age(person.getAge())
                .groups(person.getGroups().stream()
                        .map(PersonMapper::mapGroupInPerson)
                        .collect(Collectors.toList())
                )
                .build();
    }

    private static PersonTransferObject.GroupInPersonDTO mapGroupInPerson(Group group) {
        return PersonTransferObject.GroupInPersonDTO
                .builder()
                .id(group.id)
                .creationTime(group.getCreationTime())
                .meetingTime(group.getMeetingTime())
                .build();
    }

}
