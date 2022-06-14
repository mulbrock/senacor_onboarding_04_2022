package org.acme.data.util;

import org.acme.data.entities.Group;
import org.acme.data.entities.Person;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomGroupGenerator {

    public enum DateOffset {
        HOURS, DAYS, WEEKS
    }

    public static Random random = new Random();

    public static List<Group> generateRandomGroups(Set<Long> memberIDs) {
        int groupAmount = generateRandomInt(1, 10);

        Set<Group> groups = new HashSet<>();

        for (int i = 1; i <= groupAmount; i++) {
            Group group = new Group();

            LocalDateTime fromTime = LocalDateTime.now();
            DateOffset timeToJump = DateOffset.values()[generateRandomInt(0, 3)];
            LocalDateTime meetingTime = generateRandomMeetingDateTime(timeToJump, fromTime);
            group.setMeetingTime(meetingTime);

            long groupSize = randomGroupSize(memberIDs);
            populateGroupWithMembers(memberIDs, group, groupSize);

            groups.add(group);
        }
        return List.copyOf(groups);
    }

    public static long randomGroupSize(Set<Long> memberIDs) {
        long maxBound = memberIDs.size();
        if (maxBound > 10) {
            maxBound = 10;
        }
        return random.longs(2, maxBound + 1)
                .findFirst()
                .orElse(2);
    }

    public static Group populateGroupWithMembers(Set<Long> memberIDs, Group group, long groupSize) {
        while (group.getMembers().size() < groupSize) {
            long randomID = getRandomPersonID(memberIDs);
            Person person = Person.findById(randomID);
            boolean success = group.getMembers().add(person);
            if (success) {
                group.persist();
                person.getGroups().add(group);
            }
        }
        return group;
    }

    public static Long getRandomPersonID(Set<Long> memberIDs) {
        int index = generateRandomInt(0, memberIDs.size());
        Long[] memberArray = memberIDs.toArray(new Long[0]);
        return memberArray[index];
    }

    public static int generateRandomInt(int min, int max) {
        return random.ints(min, max)
                .findFirst()
                .orElse(0);
    }
    
    public static LocalDateTime generateRandomMeetingDateTime(DateOffset timeOffset, LocalDateTime fromTime) {
        LocalDateTime currentDateTime = fromTime;

        switch (timeOffset) {
            case HOURS:
                long plusHours = generateRandomLong(1, 25);
                currentDateTime = currentDateTime.plusHours(plusHours);
                break;
            case DAYS:
                long plusDays = generateRandomLong(1, 30);
                currentDateTime = currentDateTime.plusDays(plusDays);
                break;
            case WEEKS:
                long plusWeeks = generateRandomLong(1, 10);
                currentDateTime = currentDateTime.plusWeeks(plusWeeks);
                break;
        }
        return currentDateTime;
    }

    public static long generateRandomLong(int min, int max) {
        return random.longs(min, max)
                .findFirst().orElse(1);
    }
}
