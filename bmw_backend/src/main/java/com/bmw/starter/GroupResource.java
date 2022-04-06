package com.bmw.starter;

import com.bmw.starter.entities.Group;
import com.bmw.starter.entities.Person;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/groups")
@Produces(MediaType.APPLICATION_JSON)
public class GroupResource {

    @Inject
    EntityManager entityManager;

    @GET
    public List<Group> get() {
        return null;
    }

}
