package net.bmw.service;

import net.bmw.model.Group;
import net.bmw.model.Person;
import net.bmw.repository.GroupRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

//TODO: Add Logger
@ApplicationScoped
public class GroupService {
    @Inject
    GroupRepository groupRepository;

    @Inject
    PersonService personService;

    @Transactional
    public Group create(Group group) {
        groupRepository.persist(group);
        return group;
    }

    @Transactional
    public Group addPerson(Long groupId, Long personId) {
        Group foundGroup = getById(groupId);
        Person foundPerson = personService.getById(personId);

        foundGroup.addPerson(foundPerson);
        return foundGroup;
    }

    @Transactional
    public Group getById(Long id) {
        Optional<Group> optional = groupRepository.findByIdOptional(id);

        return optional.orElseThrow(() -> new NotFoundException("Group with id" + id + "does not exist"));
    }

    @Transactional
    public List<Group> getAll() {
        return groupRepository.listAll();
    }

    @Transactional
    public Set<Person> getAllPersonsByGroupId(Long groupId) {

        Group foundGroup = getById(groupId);
        return foundGroup.getPersons();
    }

    @Transactional
    // Could be done more generic
    public Group update(Long id, Group group) {
        Group foundGroup = getById(id);

        foundGroup.setMeetingTime(group.getMeetingTime());
        return foundGroup;
    }

    //TODO: Try another approach - exception
    @Transactional
    public boolean deleteById(Long id) {
        return groupRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        groupRepository.deleteAll();
    }

    public Long countGroup() {
        return groupRepository.count();
    }
}
