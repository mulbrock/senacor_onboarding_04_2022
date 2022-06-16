package org.acme.controllers;

import org.acme.controllers.mapper.PersonMapper;
import org.acme.controllers.transfer.PersonTransferObject;
import org.acme.data.services.PersonService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/api/persons")
public class PersonController {

    @Inject
    PersonService personService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<PersonTransferObject.ReadPersonDTO> persons = PersonMapper.map(personService.getAllPersons());
        return Response.ok(persons).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long personId) {
        PersonTransferObject.ReadPersonDTO personDTO = PersonMapper.map(personService.getByID(personId));
        if (personDTO != null) {
            return Response.ok(personDTO).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid PersonTransferObject.CreateUpdatePersonDTO personDTO) {
        boolean success = personService.create(personDTO);

        if (success) {
            return Response.created(URI.create("/persons")).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/create_random")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@QueryParam("amount") int amount) {
        PersonService.RandomPersonGenerator.generatePersons(amount);

        return Response.created(URI.create("/persons")).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("id") Long id, @Valid PersonTransferObject.CreateUpdatePersonDTO personDTO) {
        System.out.println("PUT CALLED FOR ID: " + id);
        System.out.println(personDTO);
        boolean success = personService.updateByID(id, personDTO);
        if (success) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Long id) {
        boolean deleted = personService.deleteByID(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
