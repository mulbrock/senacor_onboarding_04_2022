package org.acme.data;

import org.acme.controllers.transfer.PersonTransferObject;
import org.acme.data.entities.Person;

public class DummyDataCreator {

    public static PersonTransferObject.CreateUpdatePersonDTO createPersonDTO(){
        PersonTransferObject.CreateUpdatePersonDTO personDTO =
                new PersonTransferObject.CreateUpdatePersonDTO();
        personDTO.setAge(22);
        personDTO.setFirstName("Hans");
        personDTO.setLastName("Strange");

        return personDTO;
    }

}
