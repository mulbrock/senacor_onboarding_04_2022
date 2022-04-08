package com.senacor.onboardingbackend;

import com.senacor.onboardingbackend.domainobject.Group;
import com.senacor.onboardingbackend.domainobject.Person;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Fixtures {

    public static Group dummyGroup() {
        Group group = new Group();
        group.setId(ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE));
        group.setDateCreated(OffsetDateTime.now());
        group.setDateMeeting(group.getDateCreated().plusHours(ThreadLocalRandom.current().nextInt(0, 48)));
        return group;
    }

    public static List<Person> dummyPersons(int size) {
        return IntStream.range(0, size)
            .mapToObj(Fixtures::dummyPerson)
            .collect(Collectors.toList());
    }

    private static Person dummyPerson(long id) {
        Person person = new Person();
        person.setId(id);
        person.setFirstName(UUID.randomUUID().toString());
        person.setLastName(UUID.randomUUID().toString());
        person.setAge(ThreadLocalRandom.current().nextInt(0, 80));
        return person;
    }

    public static Person dummyPerson() {
        return dummyPerson(ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE));
    }

    public static boolean existsInGroup(Group group, long personId) {
        return group.getPersons().stream().anyMatch(it -> Objects.equals(it.getId(), personId));
    }

    public static boolean existsInGroup(Person person, long groupId) {
        return person.getGroups().stream().anyMatch(it -> Objects.equals(it.getId(), groupId));
    }
}
