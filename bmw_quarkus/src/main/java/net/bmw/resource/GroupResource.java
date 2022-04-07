package net.bmw.resource;

import net.bmw.model.Group;
import net.bmw.service.GroupService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/group")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON})
public class GroupResource {
    @Inject
    GroupService groupService;

    @GET
    @Path("/all")
    public Response getAll() {
        return Response.ok(groupService.getAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getGroup(@PathParam("id") Long id) {
        return Response.ok(groupService.getById(id)).build();

    }

    @POST
    public Response createGroup(Group group) {
        return Response.ok(groupService.create(group)).build();
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
