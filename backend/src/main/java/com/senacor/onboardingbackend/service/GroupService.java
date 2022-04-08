package com.senacor.onboardingbackend.service;

import com.senacor.onboardingbackend.datatransferobject.GroupDTO;
import com.senacor.onboardingbackend.domainobject.Group;
import com.senacor.onboardingbackend.domainobject.Person;
import com.senacor.onboardingbackend.exception.NotFoundException;
import com.senacor.onboardingbackend.exception.WasDeletedException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class GroupService {

    private EntityManager em;
    private PersonService personService;

    public Group get(Long id) {
        Group entity = em.find(Group.class, id);
        if (entity == null) {
            throw new NotFoundException("Cannot find Group with id=" + id);
        }
        if (entity.isDeleted()) {
            throw new WasDeletedException("Group with id=" + id + " was deleted before");
        }
        return entity;
    }

    @SuppressWarnings("unchecked")
    public List<Group> getAll() {
        return (List<Group>) em.createQuery("SELECT g FROM Group g").getResultList();
    }

    @Transactional
    public Group create(GroupDTO.CreateGroupDTO toCreate) {
        Group entity = new Group();
        entity.setDateCreated(OffsetDateTime.now());
        entity.setDateMeeting(toCreate.getDateMeeting());
        em.persist(entity);

        personService.addToGroup(toCreate.getPersonIds(), entity);

        return entity;
    }

    @Transactional
    public Group createRandom() {
        GroupCreateStrategy strategy = new GroupCreateStrategy();

        Group entity = new Group();
        entity.setDateCreated(OffsetDateTime.now());
        entity.setDateMeeting(strategy.pickRandomTime(entity.getDateCreated()));
        em.persist(entity);

        List<Person> validPersons = personService.getAll()
            .stream()
            .filter(it -> !it.isDeleted())
            .collect(Collectors.toList());

        Set<Long> randomPersonIds = strategy.pickRandomPersons(validPersons)
            .stream()
            .map(Person::getId)
            .collect(Collectors.toSet());

        personService.addToGroup(randomPersonIds, entity);

        return entity;
    }

    @Transactional
    public Group addPerson(Long groupId, Long personId) {
        Group entity = get(groupId);
        personService.addToGroup(Set.of(personId), entity);
        return entity;
    }

    @Transactional
    public void deleteById(Long id) {
        Group entity;
        try {
            entity = get(id);
        } catch (WasDeletedException e) {
            // idempotency. already deleted.
            return;
        }

        entity.setDeleted(true);
        em.merge(entity);
    }
}
