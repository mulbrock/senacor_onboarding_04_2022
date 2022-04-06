package com.senacor.onboardingbackend.datatransferobject.mapping;

import com.senacor.onboardingbackend.datatransferobject.GroupDTO;
import com.senacor.onboardingbackend.domainobject.Group;
import com.senacor.onboardingbackend.domainobject.Person;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupDTOMapperTest {

    @Test
    public void testMap() {
        EasyRandom generator = new EasyRandom();
        Group group = generator.nextObject(Group.class);
        group.setPersons(generator.objects(Person.class, 5).collect(Collectors.toSet()));

        GroupDTO.ReadGroupDTO dto = GroupDTOMapper.map(group);

        assertEquals(group.getId(), dto.getId());
        assertEquals(group.getDateCreated(), dto.getDateCreated());
        assertEquals(group.getDateMeeting(), dto.getDateMeeting());
        assertEquals(group.getPersons().size(), dto.getPersons().size());

        for (Person person : group.getPersons()) {
            Long id = person.getId();

            Optional<GroupDTO.PersonInReadGroupDTO> optional = dto.getPersons()
                .stream()
                .filter(k -> Objects.equals(k.getId(), id))
                .findFirst();

            if (optional.isEmpty()) {
                Assertions.fail();
            }

            assertPersonDto(person, optional.get());
        }
    }

    private void assertPersonDto(Person person, GroupDTO.PersonInReadGroupDTO personDto) {
        assertEquals(person.getId(), personDto.getId());
        assertEquals(person.getFirstName(), personDto.getFirstName());
        assertEquals(person.getLastName(), personDto.getLastName());
        assertEquals(person.getAge(), personDto.getAge());
    }
}
