package org.acme.controllers;

import org.acme.controllers.mapper.GroupMapper;
import org.acme.controllers.transfer.GroupTransferObject;
import org.acme.data.services.GroupService;
import org.acme.data.services.PersonService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/groups")
public class GroupController {

    @Inject
    GroupService groupService;
    @Inject
    PersonService personService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        List<GroupTransferObject.ReadGroupDTO> groups = groupService.getAllGroups();
        return Response.ok(groups).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getByID(@PathParam("id") Long id){
        GroupTransferObject.ReadGroupDTO groupDTO = groupService.getByID(id);
        return Response.ok(groupDTO).build();
    }

    @POST
    @Path("/random")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(GroupTransferObject.CreateGroupDTO groupTransferObject){
        PersonService.RandomPersonGenerator.generatePersons();

        GroupTransferObject.ReadGroupDTO createdGroup = GroupMapper.map(groupService.createGroup(groupTransferObject));

        return Response.ok(createdGroup).build();
    }

}
