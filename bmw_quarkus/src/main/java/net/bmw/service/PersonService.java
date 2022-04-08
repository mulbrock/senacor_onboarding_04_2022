package net.bmw.service;

import net.bmw.model.Group;
import net.bmw.model.Person;
import net.bmw.repository.GroupRepository;
import net.bmw.repository.PersonRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class PersonService {
    @Inject
    PersonRepository personRepository;

    @Inject
    GroupRepository groupRepository;

    @Inject
    GroupService groupService;

    @Transactional
    public Person create(Person person) {
        personRepository.persist(person);
        return person;
    }

    @Transactional
    public Person assignGroupToPerson(Long personId, Group group) {
        Person foundPerson = getById(personId);
        long groupId = group.getId();

        // Check if the group already exist
        if(groupId != 0L) {
            Group foundGroup = groupService.getById(group.getId());
            foundPerson.addGroup(foundGroup);
            return foundPerson;
        }

        foundPerson.addGroup(groupService.create(group));
        return foundPerson;
    }

    @Transactional
    public Set<Group> getAllGroupsByPersonId(Long personId) {
        Person foundPerson = getById(personId);
        return foundPerson.getGroups();
    }

    @Transactional
    public Person getById(Long id) {
        Optional<Person> optional = personRepository.findByIdOptional(id);

        return optional.orElseThrow(() -> new NotFoundException("Person with id: " + id + "does not exist."));
    }

    @Transactional
    public List<Person> getAll() {
       return personRepository.listAll();
    }

    @Transactional
    public Person update(Long id, Person person) {
        Person foundPerson = getById(id);

        foundPerson.setFirstName(person.getFirstName());
        foundPerson.setLastName(person.getLastName());
        foundPerson.setAge(person.getAge());

        return foundPerson;
    }

    @Transactional
    //Second approach of deleting an entity
    public void deleteById(Long id) {
        Person foundPerson = getById(id);
        personRepository.delete(foundPerson);
    }

    @Transactional
    public Long countPersons() {
        return personRepository.count();
    }
}
