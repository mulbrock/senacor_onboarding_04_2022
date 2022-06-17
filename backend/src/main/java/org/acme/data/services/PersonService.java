package org.acme.data.services;

import org.acme.controllers.transfer.PersonTransferObject;
import org.acme.data.entities.Group;
import org.acme.data.entities.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class PersonService {

    @Inject
    GroupService groupService;

    public List<Person> getAllPersons() {
        return Person.listAll();
    }

    public Person getByID(Long iD) {
        return Person.findById(iD);
    }

    @Transactional
    public boolean create(PersonTransferObject.CreateUpdatePersonDTO personDTO) {
        Person person = new Person();
        populateDataFromDTO(person, personDTO);
        person.persist();
        return person.isPersistent();
    }

    @Transactional
    public boolean updateByID(Long personID, PersonTransferObject.CreateUpdatePersonDTO personDTO) {
        Person personToUpdate = Person.findById(personID);
        if (personToUpdate != null) {
            populateDataFromDTO(personToUpdate, personDTO);
            personToUpdate.persist();
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteByID(Long personID) {
        Person person = Person.findById(personID);
        Set<Group> personGroups = person.getGroups();
        boolean success = Person.deleteById(personID);
        if (success) {
            groupService.removeEmptyGroups(personGroups, person);
        }
        return success;
    }

    @Transactional
    public boolean deleteGroupFromPerson(Long personID, Group group) {
        Person person = Person.findById(personID);
        if (person != null) {
            return person.getGroups().remove(group);
        }
        
        return false;
    }

    public Person populateDataFromDTO(Person person,
                                      PersonTransferObject.CreateUpdatePersonDTO personDTO) {
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setAge(personDTO.getAge());
        return person;
    }

    public Group addToGroup(List<Long> personIDs, Group group) {
        for (Long id : personIDs) {
            Person person = getByID(id);
            if (person != null) {
                person.getGroups().add(group);
                group.getMembers().add(person);
            }
        }
        return group;
    }

    public Set<Long> getAllMemberIDs() {
        List<Person> persons = Person.listAll();
        return persons.stream().map(person -> person.id).collect(Collectors.toSet());
    }

    public static class RandomPersonGenerator {

        @Transactional
        public static List<Person> generatePersons(int number) {
            List<Person> persons = new LinkedList<>();
            for (int i = 1; i <= number; i++) {
                Person person = new Person();
                person.setFirstName("Person " + i);
                person.setLastName("LastName " + i);
                person.setAge(30 + i);
                person.persist();
                persons.add(person);
            }
            return persons;
        }

    }

}
