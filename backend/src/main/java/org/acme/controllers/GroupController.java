package org.acme.controllers;

import org.acme.controllers.mapper.GroupMapper;
import org.acme.controllers.transfer.GroupTransferObject;
import org.acme.data.services.GroupService;
import org.acme.data.services.PersonService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Path("/api/groups")
public class GroupController {

    @Inject
    GroupService groupService;
    @Inject
    PersonService personService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<GroupTransferObject.ReadGroupDTO> groups = groupService.getAllGroups();
        return Response.ok(groups).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getByID(@PathParam("id") Long id) {
        GroupTransferObject.ReadGroupDTO groupDTO = groupService.getByID(id);
        return Response.ok(groupDTO).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid GroupTransferObject.CreateGroupDTO groupTransferObject) {

        GroupTransferObject.ReadGroupDTO createdGroup = GroupMapper.map(
                groupService.createGroup(groupTransferObject));
        return Response.ok(createdGroup).build();
    }

    @POST
    @Path("/create_random")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRandom() {
        Set<Long> memberIDs = personService.getAllMemberIDs();

        try {
            List<GroupTransferObject.ReadGroupDTO> createdGroups = GroupMapper.map(
                    groupService.generateRandomGroups(memberIDs));
            return Response.ok(createdGroups).build();
        } catch (GroupService.NumberToSmallException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
