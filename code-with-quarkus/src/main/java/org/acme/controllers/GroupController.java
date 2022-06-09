package org.acme.controllers;

import org.acme.data.GroupService;
import org.acme.data.PersonService;
import org.acme.data.entities.Group;
import org.acme.data.transfer.GroupTransferObject;

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
        List<Group> groups = Group.listAll();
        return Response.ok(groups).build();
    }

    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(GroupTransferObject groupTransferObject){

        Group createdGroup = groupService.createGroup(groupTransferObject);
        //personService.addToGroup(groupTransferObject.getMemberIDs(), createdGroup);

        return Response.ok(createdGroup).build();
    }

}
