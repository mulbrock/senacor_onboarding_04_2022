package org.acme.data;

import org.acme.data.entities.Group;
import org.acme.data.entities.Person;
import org.acme.controllers.transfer.GroupTransferObject;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

@ApplicationScoped
public class GroupService {

    private PersonService personService = new PersonService();

    /*
    @Transactional
    public List<Group> createGroups(int groupNumber){
        List<Group> groups = new ArrayList<>(groupNumber);
        for (int i = 0; i < groupNumber; i++){
            Group group = new Group();
            group.setMembers(new HashSet<>());
            group.persist();
            groups.add(group);
        }
        return groups;
    }

     */

    @Transactional
    public Group createGroup(GroupTransferObject groupTransferObject){
        Group group = new Group();
        group.setMembers(new HashSet<>());
        //addPersonsToGroup(groupTransferObject.getMemberIDs(), group);

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
}
