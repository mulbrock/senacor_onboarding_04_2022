package com.senacor.onboardingbackend.datatransferobject.mapping;

import com.senacor.onboardingbackend.datatransferobject.PersonDTO;
import com.senacor.onboardingbackend.domainobject.Group;
import com.senacor.onboardingbackend.domainobject.Person;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonDTOMapperTest {

    @Test
    public void testMap() {
        EasyRandom generator = new EasyRandom();
        Person person = generator.nextObject(Person.class);
        person.setGroups(generator.objects(Group.class, 5).collect(Collectors.toSet()));

        PersonDTO.ReadPersonDTO dto = PersonDTOMapper.map(person);

        assertEquals(person.getId(), dto.getId());
        assertEquals(person.getFirstName(), dto.getFirstName());
        assertEquals(person.getLastName(), dto.getLastName());
        assertEquals(person.getAge(), dto.getAge());
        assertEquals(person.getGroups().size(), dto.getGroups().size());

        for (Group group : person.getGroups()) {
            Long id = group.getId();

            Optional<PersonDTO.GroupInReadPersonDTO> optional = dto.getGroups()
                .stream()
                .filter(k -> Objects.equals(k.getId(), id))
                .findFirst();

            if (optional.isEmpty()) {
                Assertions.fail();
            }

            assertGroupDto(group, optional.get());
        }
    }

    private static void assertGroupDto(Group group, PersonDTO.GroupInReadPersonDTO groupDto) {
        assertEquals(group.getId(), groupDto.getId());
        assertEquals(group.getDateCreated(), groupDto.getDateCreated());
        assertEquals(group.getDateMeeting(), groupDto.getDateMeeting());
    }
}
