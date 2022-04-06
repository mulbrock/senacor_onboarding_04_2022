package com.bmw.starter;

import com.bmw.starter.entities.Person;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    EntityManager entityManager;

    @GET
    public List<Person> get() {
        return null;
    }

}
