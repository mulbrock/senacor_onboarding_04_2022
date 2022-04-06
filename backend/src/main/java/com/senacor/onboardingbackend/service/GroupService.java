package com.senacor.onboardingbackend.service;

import com.senacor.onboardingbackend.datatransferobject.GroupDTO;
import com.senacor.onboardingbackend.domainobject.Group;
import com.senacor.onboardingbackend.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

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
    public Group addPerson(Long groupId, Long personId) {
        Group entity = get(groupId);
        personService.addToGroup(Set.of(personId), entity);
        return entity;
    }

    @Transactional
    public void deleteById(Long id) {
        Group entity = get(id);

        em.createNativeQuery("delete from group_person where group_id = :group_id")
            .setParameter("group_id", id)
            .executeUpdate();

        em.remove(entity);
    }
}
