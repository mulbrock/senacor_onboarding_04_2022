package org.acme.data;

import org.acme.data.entities.Group;
import org.acme.data.entities.Person;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class PersonService {

    public void addToGroup(List<Long> personIDs, Group group){
        for(Long id : personIDs){
            Person person = Person.findById(id);
            if (person != null){

                person.getGroups().add(group);
                group.getMembers().add(person);

            }
        }
    }

}
