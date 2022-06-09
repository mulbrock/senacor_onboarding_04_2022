package org.acme.data;

import org.acme.controllers.mapper.GroupMapper;
import org.acme.data.entities.Group;
import org.acme.data.entities.Person;
import org.acme.controllers.transfer.GroupTransferObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

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
}
