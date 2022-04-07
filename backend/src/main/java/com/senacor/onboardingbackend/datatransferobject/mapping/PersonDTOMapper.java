package com.senacor.onboardingbackend.datatransferobject.mapping;

import com.senacor.onboardingbackend.datatransferobject.PersonDTO;
import com.senacor.onboardingbackend.domainobject.Group;
import com.senacor.onboardingbackend.domainobject.Person;

import java.util.List;
import java.util.stream.Collectors;

public class PersonDTOMapper {

    public static List<PersonDTO.ReadPersonDTO> map(List<Person> persons) {
        return persons.stream().map(PersonDTOMapper::map).collect(Collectors.toList());
    }

    public static PersonDTO.ReadPersonDTO map(Person person) {
        if (person == null) {
            return null;
        }

        return PersonDTO.ReadPersonDTO
            .builder()
            .id(person.getId())
            .firstName(person.getFirstName())
            .lastName(person.getLastName())
            .age(person.getAge())
            .deleted(person.isDeleted())
            .groups(person.getGroups().stream().map(PersonDTOMapper::map).collect(Collectors.toList()))
            .build();
    }

    private static PersonDTO.GroupInReadPersonDTO map(Group group) {
        return PersonDTO.GroupInReadPersonDTO
            .builder()
            .id(group.getId())
            .dateCreated(group.getDateCreated())
            .dateMeeting(group.getDateMeeting())
            .deleted(group.isDeleted())
            .build();
    }
}
