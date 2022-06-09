package org.acme.data;

import org.acme.controllers.transfer.PersonTransferObject;
import org.acme.data.entities.Group;
import org.acme.data.entities.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PersonService {

    public List<Person> getAllPersons(){
        return Person.listAll();
    }

    public Person getByID(Long iD){
        return Person.findById(iD);
    }

    @Transactional
    public boolean create(PersonTransferObject.CreateUpdatePersonDTO personDTO){
        Person person = new Person();
        populateDataFromDTO(person, personDTO);
        person.persist();
        return person.isPersistent();
    }

    @Transactional
    public boolean updateByID(Long personID, PersonTransferObject.CreateUpdatePersonDTO personDTO){
        Person personToUpdate = Person.findById(personID);
        if (personToUpdate != null){
            populateDataFromDTO(personToUpdate, personDTO);
            personToUpdate.persist();
            return personToUpdate.isPersistent();
        }
        return false;
    }

    @Transactional
    public boolean deleteByID(Long personID){
        return Person.deleteById(personID);
    }

    private void populateDataFromDTO(Person person,
                                     PersonTransferObject.CreateUpdatePersonDTO personDTO){
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setAge(personDTO.getAge());
    }

    public void addToGroup(List<Long> personIDs, Group group){
        for(Long id : personIDs){
            Person person = Person.findById(id);
            if (person != null){
                person.getGroups().add(group);
            }
        }
    }

}
