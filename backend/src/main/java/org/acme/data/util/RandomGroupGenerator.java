package org.acme.data.util;

import org.acme.data.entities.Group;
import org.acme.data.entities.Person;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomGroupGenerator {

    private static final Random random = new Random();

    public static List<Group> generateRandomGroups(Set<Long> memberIDs) {
        int groupAmount = random.nextInt(10);
        Set<Group> groups = new HashSet<>();
        for (int i = 1; i <= groupAmount; i++) {
            Group group = new Group();

            int timeOffset = random.ints(0, 3)
                    .findFirst().orElse(0);
            LocalDateTime fromTime = LocalDateTime.now();
            LocalDateTime meetingTime = generateRandomDateTime(timeOffset, fromTime);
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

    public static void populateGroupWithMembers(Set<Long> memberIDs, Group group, long groupSize) {
        while (group.getMembers().size() < groupSize) {
            long randomID = getRandomPersonID(memberIDs);
            Person person = Person.findById(randomID);
            boolean success = group.getMembers().add(person);
            if (success) {
                group.persist();
                person.getGroups().add(group);
            }
        }
    }

    public static Long getRandomPersonID(Set<Long> memberIDs) {
        int index = random.ints(0, memberIDs.size())
                .findFirst()
                .orElse(0);
        Long[] memberArray = memberIDs.toArray(new Long[0]);
        return memberArray[index];
    }

    public static LocalDateTime generateRandomDateTime(int timeOffset, LocalDateTime fromTime) {
        LocalDateTime currentDateTime = fromTime;

        switch (timeOffset) {
            case 0:
                long plusHours = random.longs(1, 25)
                        .findFirst().orElse(1);
                currentDateTime = currentDateTime.plusHours(plusHours);
                break;
            case 1:
                long plusDays = random.longs(1, 30)
                        .findFirst().orElse(1);
                currentDateTime = currentDateTime.plusDays(plusDays);
                break;
            case 2:
                long plusWeeks = random.longs(1, 12)
                        .findFirst().orElse(1);
                currentDateTime = currentDateTime.plusWeeks(plusWeeks);
                break;
        }
        return currentDateTime;
    }
}
