package net.bmw.resource;

import net.bmw.model.Group;
import net.bmw.model.Person;
import net.bmw.service.PersonService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {
    @Inject
    PersonService personService;

    @GET
    @Path("/{id}")
    public Response getPerson(@PathParam("id") Long id) {
        return Response.ok(personService.getById(id)).build();
    }

    @GET
    @Path("/all")
    public Response getAll() {
        return Response.ok(personService.getAll()).build();
    }

    @POST
    @Transactional
    public Response createPerson(Person person) {
        return Response.ok(personService.create(person)).build();
    }

    @POST
    @Path("/{id}/group")
    public Response assignGroupToPerson(@PathParam("id") Long id, Group group) {
        return Response.ok(personService.assignGroupToPerson(id, group)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePerson(@PathParam("id") Long id, Person person) {
        return Response.ok(personService.update(id, person)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") Long id) {
        personService.deleteById(id);
        return Response.ok().build();
    }

    @GET
    @Path("/count")
    public Response countPersons() {
        return Response.ok(personService.countPersons()).build();
    }
}
