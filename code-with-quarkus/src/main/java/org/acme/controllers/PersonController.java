package org.acme.controllers;

import org.acme.controllers.mapper.PersonMapper;
import org.acme.controllers.transfer.PersonTransferObject;
import org.acme.data.PersonService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/persons")
public class PersonController {

    @Inject
    PersonService personService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        List<PersonTransferObject.ReadPersonDTO> persons = PersonMapper.map(personService.getAllPersons());
        return Response.ok(persons).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long personId){
        PersonTransferObject.ReadPersonDTO personDTO = PersonMapper.map(personService.getByID(personId));
        if (personDTO != null){
            return Response.ok(personDTO).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(PersonTransferObject.CreateUpdatePersonDTO personDTO){
        boolean success = personService.create(personDTO);

        if(success){
            return Response.created(URI.create("/persons")).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("id") Long id, PersonTransferObject.CreateUpdatePersonDTO personDTO){
        if (personDTO.getFirstName().equals("") || personDTO.getLastName().equals("") || personDTO.getAge() < 0){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        boolean success = personService.updateByID(id, personDTO);
        if (success){
            return Response.created(URI.create("/persons")).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Transactional
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") Long id){
        boolean deleted = personService.deleteByID(id);
        if (deleted){
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
