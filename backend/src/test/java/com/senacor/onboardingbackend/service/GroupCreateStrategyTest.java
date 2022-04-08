package com.senacor.onboardingbackend.service;

import com.senacor.onboardingbackend.domainobject.Person;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.senacor.onboardingbackend.Fixtures.dummyPerson;
import static com.senacor.onboardingbackend.Fixtures.dummyPersons;

public class GroupCreateStrategyTest {

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -50, -1, 0, 1})
    public void testConstructor_minGroupSize(int minGroupSize) {
        IllegalArgumentException thrown = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new GroupCreateStrategy(minGroupSize, 10, 9, 20)
        );

        Assertions.assertEquals("minGroupSize cannot be smaller than 2", thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -50, -1, 0, 1})
    public void testConstructor_maxGroupSize(int maxGroupSize) {
        IllegalArgumentException thrown = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new GroupCreateStrategy(2, maxGroupSize, 9, 20)
        );

        Assertions.assertEquals("maxGroupSize cannot be smaller than minGroupSize", thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -50, -2, -1, 24, 25, 26, 50, Integer.MAX_VALUE})
    public void testConstructor_earliestHour(int earliestHour) {
        IllegalArgumentException thrown = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new GroupCreateStrategy(2, 10, earliestHour, 23)
        );

        Assertions.assertEquals("earliestHour and latestHour are not valid", thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -50, -2, -1, 24, 25, 26, 50, Integer.MAX_VALUE})
    public void testConstructor_latestHour(int latestHour) {
        IllegalArgumentException thrown = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new GroupCreateStrategy(2, 10, 0, latestHour)
        );

        Assertions.assertEquals("earliestHour and latestHour are not valid", thrown.getMessage());
    }

    @Test
    public void testConstructor_earliestAndLatestHour() {
        List<Pair<Integer, Integer>> validCombinations = List.of(
            Pair.of(0, 23),
            Pair.of(2, 20),
            Pair.of(8, 16),
            Pair.of(12, 14)
        );

        for (Pair<Integer, Integer> pair : validCombinations) {
            Assertions.assertDoesNotThrow(() -> new GroupCreateStrategy(2, 10, pair.getLeft(), pair.getRight()));
        }
    }

    @Test
    public void testConstructor_invalidEarliestAndLatestHour() {
        List<Pair<Integer, Integer>> validCombinations = List.of(
            Pair.of(13, 13),
            Pair.of(16, 12),
            Pair.of(18, 10),
            Pair.of(20, 4)
        );

        for (Pair<Integer, Integer> pair : validCombinations) {
            IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new GroupCreateStrategy(2, 10, pair.getLeft(), pair.getRight())
            );

            Assertions.assertEquals("earliestHour and latestHour are not valid", thrown.getMessage());
        }
    }

    @Test
    public void testPickRandomPersons_invalidSize() {
        Person p1 = dummyPerson();

        GroupCreateStrategy strategy = new GroupCreateStrategy();

        IllegalArgumentException thrown = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> strategy.pickRandomPersons(List.of(p1))
        );

        Assertions.assertEquals("There are not enough persons to create a group", thrown.getMessage());
    }

    @Test
    public void testPickRandomPersons_SizeTwo() {
        Person p1 = dummyPerson();
        Person p2 = dummyPerson();
        List<Person> allPersons = List.of(p1, p2);

        GroupCreateStrategy strategy = new GroupCreateStrategy();
        List<Person> randomPersons = strategy.pickRandomPersons(allPersons);

        Assertions.assertEquals(allPersons, randomPersons);
    }

    @Test
    public void testPickRandomPersons() {
        List<Person> allPersons = dummyPersons(1000);
        int randomizationCount = 500;

        GroupCreateStrategy strategy = new GroupCreateStrategy();

        Set<List<Person>> set = new HashSet<>();
        for (int i = 0; i < randomizationCount; i++) {
            set.add(strategy.pickRandomPersons(allPersons));
        }

        Assertions.assertEquals(randomizationCount, set.size());
    }

    @Test
    public void testPickRandomTime() {
        OffsetDateTime now = OffsetDateTime.now();
        GroupCreateStrategy strategy = new GroupCreateStrategy();

        OffsetDateTime time = strategy.pickRandomTime(now);

        Assertions.assertEquals(0, time.getNano());
        Assertions.assertEquals(0, time.getSecond());
        Assertions.assertEquals(0, time.getMinute());
        Assertions.assertEquals(0, time.getMinute());

        Assertions.assertNotEquals(now, time);
        Assertions.assertTrue(time.isAfter(now));

        Assertions.assertTrue(time.getHour() >= strategy.getEarliestHour());
        Assertions.assertTrue(time.getHour() <= strategy.getLatestHour());
    }
}