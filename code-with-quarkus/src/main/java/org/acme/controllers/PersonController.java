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
                .map(person -> Response.ok(person).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Person person){
        Person.persist(person);
        if(person.isPersistent()){
            return Response.created(URI.create("/persons" + person.id)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Transactional
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("id") Long id, Person person){
        if (person.firstName.equals("") || person.lastName.equals("") || person.age < 0){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Person personToUpdate = Person.findById(id);

        if(personToUpdate == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        personToUpdate.firstName = person.firstName;
        personToUpdate.lastName = person.lastName;
        personToUpdate.age = person.age;

        personToUpdate.persist();
        if(personToUpdate.isPersistent()){
            return Response.created(URI.create("/persons" + person.id)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Transactional
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Long id){
        boolean deleted = Person.deleteById(id);
        if (deleted){
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
