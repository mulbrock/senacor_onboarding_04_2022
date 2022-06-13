package org.acme.data;

import io.quarkus.test.common.QuarkusTestResource;
import org.acme.data.entities.Group;
import org.acme.data.entities.Person;

import javax.transaction.Transactional;

@QuarkusTestResource(PostgresDB.class)
public class DatabaseTest {

    @Transactional
    public Person findPersonByID(Long id){
        return Person.findById(id);
    }

    @Transactional
    public Group findGroupByID(Long id){
        return Group.findById(id);
    }

    @Transactional
    public void savePerson(Person person){
        person.persist();
    }

    @Transactional
    public void saveGroup(Group group){
        group.persist();
    }
}
