package com.senacor.onboardingbackend.service;

import com.senacor.onboardingbackend.domainobject.Person;
import lombok.Getter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class GroupCreateStrategy {

    /**
     * This value determines the time window for generating random times: [now, now + TIME_HORIZON_IN_DAYS]
     */
    private static final int TIME_HORIZON_IN_DAYS = 7;

    private final int minGroupSize;
    private final int maxGroupSize;

    private final int earliestHour;
    private final int latestHour;

    public GroupCreateStrategy() {
        this(2, 20, 9, 20);
    }

    public GroupCreateStrategy(int minGroupSize, int maxGroupSize, int earliestHour, int latestHour) {
        if (minGroupSize < 2) {
            throw new IllegalArgumentException("minGroupSize cannot be smaller than 2");
        }
        if (maxGroupSize < minGroupSize) {
            throw new IllegalArgumentException("maxGroupSize cannot be smaller than minGroupSize");
        }
        if (!isValidHour(earliestHour) || !isValidHour(latestHour) || earliestHour >= latestHour) {
            throw new IllegalArgumentException("earliestHour and latestHour are not valid");
        }
        this.minGroupSize = minGroupSize;
        this.maxGroupSize = maxGroupSize;
        this.earliestHour = earliestHour;
        this.latestHour = latestHour;
    }

    public List<Person> pickRandomPersons(List<Person> persons) {
        if (persons.size() < minGroupSize) {
            throw new IllegalArgumentException("There are not enough persons to create a group");
        }
        if (persons.size() == minGroupSize) {
            return persons;
        }

        int max = Math.min(persons.size(), maxGroupSize);
        int groupSize = ThreadLocalRandom.current().nextInt(minGroupSize, max);

        Set<Person> randomPersons = new HashSet<>();
        while (randomPersons.size() < groupSize) {
            int index = ThreadLocalRandom.current().nextInt(0, persons.size());
            randomPersons.add(persons.get(index));
        }

        return new ArrayList<>(randomPersons);
    }

    public OffsetDateTime pickRandomTime(OffsetDateTime now) {
        OffsetDateTime start = now.truncatedTo(ChronoUnit.HOURS).plusHours(1);
        OffsetDateTime end = start.plusDays(TIME_HORIZON_IN_DAYS);

        long startSeconds = start.toEpochSecond();
        long endSeconds = end.toEpochSecond();

        long random = ThreadLocalRandom.current().nextLong(startSeconds, endSeconds);
        OffsetDateTime result = OffsetDateTime.ofInstant(Instant.ofEpochSecond(random), now.getOffset());

        while (!isValid(result.getHour())) {
            random = ThreadLocalRandom.current().nextLong(startSeconds, endSeconds);
            result = OffsetDateTime.ofInstant(Instant.ofEpochSecond(random), start.getOffset());
        }

        return result.truncatedTo(ChronoUnit.HOURS);
    }

    private boolean isValid(int hour) {
        return isWithinBounds(hour, earliestHour, latestHour);
    }

    private static boolean isValidHour(int toCheck) {
        return isWithinBounds(toCheck, 0, 23);
    }

    private static boolean isWithinBounds(int toCheck, int rangeStartInclusive, int rangeEndInclusive) {
        return (toCheck >= rangeStartInclusive) && (toCheck <= rangeEndInclusive);
    }
}
