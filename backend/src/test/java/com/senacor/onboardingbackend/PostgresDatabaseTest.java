package com.senacor.onboardingbackend;

import com.senacor.onboardingbackend.domainobject.Group;
import com.senacor.onboardingbackend.domainobject.Person;
import io.quarkus.test.common.QuarkusTestResource;
import org.junit.jupiter.api.AfterEach;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static com.senacor.onboardingbackend.Fixtures.dummyGroup;
import static com.senacor.onboardingbackend.Fixtures.dummyPerson;

@QuarkusTestResource(PostgresDatabase.class)
public abstract class PostgresDatabaseTest {

    @Inject
    protected EntityManager em;

    @AfterEach
    @Transactional
    public void afterEach() {
        em.createNativeQuery("truncate \"group_person\", \"group\", \"person\"").executeUpdate();
    }

    @Transactional
    public <T> T save(T entity) {
        em.persist(entity);
        return entity;
    }

    @Transactional
    public <T> T findById(Class<T> entityClass, Long id) {
        return em.find(entityClass, id);
    }

    public Person findPerson(Long id) {
        return findById(Person.class, id);
    }

    public Group findGroup(Long id) {
        return findById(Group.class, id);
    }

    public Person saveRandomPerson() {
        Person toSave = dummyPerson();
        toSave.setId(null);
        return save(toSave);
    }

    public Group saveRandomGroup() {
        Group toSave = dummyGroup();
        toSave.setId(null);
        return save(toSave);
    }
}
