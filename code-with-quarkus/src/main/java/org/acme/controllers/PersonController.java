package org.acme.controllers;

import org.acme.data.entities.Person;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/persons")
public class PersonController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        List<Person> persons = Person.listAll();
        return Response.ok(persons).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long personId){
        return Person.findByIdOptional(personId)
                .map(person -> Response.ok().build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Person person){
        Person.persist(person);
        if(person.isPersistent()){
            return Response.created(URI.create("/persons" + person.getId())).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
