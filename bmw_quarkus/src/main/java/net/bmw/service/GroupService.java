package net.bmw.service;

import net.bmw.model.Group;
import net.bmw.repository.GroupRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

//TODO: Add Logger
@ApplicationScoped
public class GroupService {
    @Inject
    GroupRepository groupRepository;

    @Transactional
    public Group create(Group group) {
        groupRepository.persist(group);
        return group;
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

//    //TODO: Implement me
//    @Transactional
//    public Group addPerson(Long personId, Long groupId) {
//        Group foundGroup = groupRepository.findById(groupId);
//
//        foundGroup.getPersons().add()
//        return null;
//    }

    public Long countGroup() {
        return groupRepository.count();
    }
}
