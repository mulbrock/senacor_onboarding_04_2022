package org.acme.data.services;

import org.acme.controllers.mapper.GroupMapper;
import org.acme.controllers.transfer.GroupTransferObject;
import org.acme.data.entities.Group;
import org.acme.data.entities.Person;
import org.acme.data.util.RandomGroupGenerator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class GroupService {

    @Inject
    PersonService personService;

    @Transactional
    public Group createGroup(GroupTransferObject.CreateGroupDTO groupTransferObject) {
        Group group = new Group();
        group.setMeetingTime(groupTransferObject.getMeetingTime());

        group.setMembers(new HashSet<>());
        addPersonsToGroup(groupTransferObject.getMemberIDs(), group);

        group.persist();

        personService.addToGroup(groupTransferObject.getMemberIDs(), group);
        return group;
    }

    @Transactional
    public List<Group> generateRandomGroups(Set<Long> memberIDs) {
        if (memberIDs.size() > 1) {
            return RandomGroupGenerator.generateRandomGroups(memberIDs);
        } else {
            throw new NumberToSmallException();
        }
    }

    @Transactional
    public boolean deleteByID(Long id) {
        Group group = Group.findById(id);
        if (group != null) {
            group.getMembers().forEach(person -> this.personService.deleteGroupFromPerson(person.id, group));
        }
        return Group.deleteById(id);

    }

    @Transactional
    public void removeEmptyGroups(Set<Group> groups, Person removedPerson) {
        groups.forEach(group -> {
            if (group.getMembers().size() <= 1 && group.getMembers().contains(removedPerson)) {
                Group.deleteById(group.id);
            }
        });
    }

    public Group addPersonsToGroup(List<Long> personIDs, Group group) {
        for (Long personID : personIDs) {
            Person person = Person.findById(personID);
            if (person != null) {
                group.getMembers().add(person);
            }
        }
        return group;
    }

    public List<GroupTransferObject.ReadGroupDTO> getAllGroups() {
        List<Group> groups = Group.listAll();
        return GroupMapper.map(groups);
    }

    public GroupTransferObject.ReadGroupDTO getByID(Long id) {
        Group group = Group.findById(id);
        return GroupMapper.map(group);
    }

    public static class NumberToSmallException extends RuntimeException {
        public NumberToSmallException() {
            super("A group must contain at least two members.");
        }

    }
}
