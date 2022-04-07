package com.senacor.onboardingbackend;

import com.senacor.onboardingbackend.domainobject.Group;
import com.senacor.onboardingbackend.domainobject.Person;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Fixtures {

    public static Group dummyGroup() {
        Group group = new Group();
        group.setId(ThreadLocalRandom.current().nextLong(0, 5000L));
        group.setDateCreated(OffsetDateTime.now());
        group.setDateMeeting(group.getDateCreated().plusHours(ThreadLocalRandom.current().nextInt(0, 48)));
        return group;
    }

    public static Person dummyPerson() {
        Person person = new Person();
        person.setId(ThreadLocalRandom.current().nextLong(0, 5000L));
        person.setFirstName(UUID.randomUUID().toString());
        person.setLastName(UUID.randomUUID().toString());
        person.setAge(ThreadLocalRandom.current().nextInt(0, 80));
        return person;
    }

    public static boolean existsInGroup(Group group, long personId) {
        return group.getPersons().stream().anyMatch(it -> Objects.equals(it.getId(), personId));
    }

    public static boolean existsInGroup(Person person, long groupId) {
        return person.getGroups().stream().anyMatch(it -> Objects.equals(it.getId(), groupId));
    }
}
