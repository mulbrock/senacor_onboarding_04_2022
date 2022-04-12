package net.bmw.resource;

import net.bmw.dto.GroupDto;
import net.bmw.dto.PersonDto;
import net.bmw.mapper.GroupMapper;
import net.bmw.mapper.PersonMapper;
import net.bmw.model.Group;
import net.bmw.model.Person;
import net.bmw.service.PersonService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {
    @Inject
    PersonService personService;

    @Inject
    PersonMapper personMapper;

    @Inject
    GroupMapper groupMapper;

    @GET
    @Path("/{id}")
    public Response getPerson(@PathParam("id") Long id) {
        return Response.ok(personMapper.toDto(personService.getById(id))).build();
    }

    @GET
    public Response getAll() {
        return Response.ok(personMapper.toDtoList(personService.getAll())).build();
    }

    @GET
    @Path("/{id}/groups")
    public Response getAllGroupsOfPerson(@PathParam("id") Long id) {
        return Response.ok(groupMapper.toDtoSet(personService.getAllGroupsByPersonId(id))).build();
    }

    @POST
    @Transactional
    public Response createPerson(@Valid PersonDto personDto) {
        return Response.ok(personMapper.toDto(personService.create(personMapper.toEntity(personDto)))).build();
    }

    @POST
    @Path("/{id}/group")
    public Response assignGroupToPerson(@PathParam("id") Long id, GroupDto groupDto) {
        return Response.ok(personMapper.toDto(personService.assignGroupToPerson(id, groupMapper.toEntity(groupDto)))).build();
    }

    @POST
    @Path("/{id}/group/{groupId}")
    public Response assignGroupToPersonByGroupId(@PathParam("id") Long id, @PathParam("groupId") Long groupId) {
        return Response.ok(personMapper.toDto(personService.assignGroupToPersonByGroupId(id, groupId))).build();
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
