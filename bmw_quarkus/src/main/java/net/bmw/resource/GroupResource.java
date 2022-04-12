package net.bmw.resource;

import net.bmw.dto.GroupDto;
import net.bmw.mapper.GroupMapper;
import net.bmw.mapper.PersonMapper;
import net.bmw.model.Group;
import net.bmw.service.GroupService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON})
public class GroupResource {
    @Inject
    GroupService groupService;

    @Inject
    GroupMapper groupMapper;

    @Inject
    PersonMapper personMapper;

    @GET
    public Response getAll() {
        return Response.ok(groupMapper.toDtoList(groupService.getAll())).build();
    }

    @GET
    @Path("/{id}")
    public Response getGroup(@PathParam("id") Long id) {
        return Response.ok(groupMapper.toDto(groupService.getById(id))).build();
    }

    @GET
    @Path("/{id}/persons")
    public Response getAllPersonsByGroupId(@PathParam("id") Long id) {
        return Response.ok(groupService.getAllPersonsByGroupId(id)).build();
    }

    @POST
    public Response createGroup(@Valid GroupDto groupDto) {
        Group createdGroup = groupService.create(groupMapper.toEntity(groupDto));
        return Response.ok(groupMapper.toDto(createdGroup)).build();
    }

    @POST
    @Path("/{id}/persons/{personId}")
    public Response addPerson(@PathParam("id") Long id, @PathParam("personId") Long personId) {
        return Response.ok(groupService.addPerson(id, personId)).build();
    }
    @PUT
    @Path("/{id}")
    public Response updateGroup(@PathParam("id") Long id, Group group) {
        return Response.ok(groupService.update(id, group)).build() ;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteGroupById(@PathParam("id") Long id){
        return Response.ok(groupService.deleteById(id)).build();
    }

    @DELETE
    @Path("/all}")
    public Response deleteAllGroups(){
        groupService.deleteAll();
        return Response.ok().build();
    }

    @GET
    @Path("/count")
    public Response countGroups() {
        return Response.ok(groupService.countGroup()).build();
    }




}
