package com.senacor.onboardingbackend.service;

import com.senacor.onboardingbackend.datatransferobject.PersonDTO;
import com.senacor.onboardingbackend.domainobject.Group;
import com.senacor.onboardingbackend.domainobject.Person;
import com.senacor.onboardingbackend.exception.NotFoundException;
import com.senacor.onboardingbackend.exception.WasDeletedException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PersonService {

    private EntityManager em;

    public Person get(Long id) {
        Person entity = em.find(Person.class, id);
        if (entity == null) {
            throw new NotFoundException("Cannot find Person with id=" + id);
        }
        if (entity.isDeleted()) {
            throw new WasDeletedException("Person with id=" + id + " was deleted before");
        }
        return entity;
    }

    @SuppressWarnings("unchecked")
    public List<Person> getAll() {
        return (List<Person>) em.createQuery("SELECT p FROM Person p").getResultList();
    }

    @Transactional
    public Person create(PersonDTO.CreateUpdatePersonDTO toCreate) {
        Person entity = new Person();
        setFieldsFromDTO(toCreate, entity);
        em.persist(entity);
        return entity;
    }

    @Transactional
    public Person updateById(Long id, PersonDTO.CreateUpdatePersonDTO toUpdate) {
        Person entity = get(id);
        setFieldsFromDTO(toUpdate, entity);
        return em.merge(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        Person entity;
        try {
            entity = get(id);
        } catch (WasDeletedException e) {
            // idempotency. already deleted.
            return;
        }

        entity.setDeleted(true);
        em.merge(entity);
    }

    public void addToGroup(Set<Long> personIds, Group group) {
        for (Long personId : personIds) {
            Person person = get(personId);

            person.getGroups().add(group);
            group.getPersons().add(person);

            em.merge(person);
        }
    }

    private void setFieldsFromDTO(PersonDTO.CreateUpdatePersonDTO dto, Person entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setAge(dto.getAge());
    }
}
