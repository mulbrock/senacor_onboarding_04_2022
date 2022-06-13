package org.acme.data.services;

import org.acme.controllers.mapper.GroupMapper;
import org.acme.controllers.transfer.GroupTransferObject;
import org.acme.data.entities.Group;
import org.acme.data.entities.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@ApplicationScoped
public class GroupService {

    @Inject
    PersonService personService;

    @Transactional
    public Group createGroup(GroupTransferObject.CreateGroupDTO groupTransferObject){
        Group group = new Group();
        group.setMeetingTime(groupTransferObject.getMeetingTime());

        group.setMembers(new HashSet<>());
        addPersonsToGroup(groupTransferObject.getMemberIDs(), group);

        group.persist();

        personService.addToGroup(groupTransferObject.getMemberIDs(), group);
        return group;
    }

    @Transactional
    public List<Group> generateRandomGroups(Set<Long> memberIDs){
        return RandomGroupGenerator.generateRandomGroups(memberIDs);

    }

    public void addPersonsToGroup(List<Long> personIDs, Group group){
        for(Long personID : personIDs){
            Person person = Person.findById(personID);
            if (person != null){
                group.getMembers().add(person);
            }
        }
    }

    public List<GroupTransferObject.ReadGroupDTO> getAllGroups(){
        List<Group> groups = Group.listAll();
        return GroupMapper.map(groups);
    }

    public GroupTransferObject.ReadGroupDTO getByID(Long id){
        Group group = Group.findById(id);
        return GroupMapper.map(group);
    }

    public static class RandomGroupGenerator{

        private static final Random random = new Random();


        private static List<Group> generateRandomGroups(Set<Long> memberIDs){
            int groupAmount = random.nextInt(10);
            Set<Group> groups = new HashSet<>();
            for (int i = 1; i <= groupAmount; i++){
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

        public static long randomGroupSize(Set<Long> memberIDs){
            long maxBound = memberIDs.size();
            if (maxBound > 10){
                maxBound = 10;
            }
            return random.longs(2, maxBound)
                    .findFirst()
                    .orElse(2);
        }

        public static void populateGroupWithMembers(Set<Long> memberIDs, Group group, long groupSize){
            while (group.getMembers().size() < groupSize){
                long randomID = getRandomPersonID(memberIDs);
                Person person = Person.findById(randomID);
                boolean success = group.getMembers().add(person);
                if (success){
                    group.persist();
                    person.getGroups().add(group);
                }
            }
        }

        public static Long getRandomPersonID(Set<Long> memberIDs){
            int index = random.ints(0, memberIDs.size())
                    .findFirst()
                    .orElse(0);
            Long[] memberArray = memberIDs.toArray(new Long[0]);
            return memberArray[index];
        }

        public static LocalDateTime generateRandomDateTime(int timeOffset, LocalDateTime fromTime){
            LocalDateTime currentDateTime = fromTime;

            switch (timeOffset){
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
}
