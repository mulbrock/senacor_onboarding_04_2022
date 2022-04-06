package com.senacor.onboardingbackend.resource;

import com.senacor.onboardingbackend.datatransferobject.GroupDTO;
import com.senacor.onboardingbackend.datatransferobject.mapping.GroupDTOMapper;
import com.senacor.onboardingbackend.service.GroupService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/api/v1/groups")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class GroupResource {

    private GroupService groupService;

    @Operation(summary = "Creates a new group with the specified meeting date and adds the persons with the specified ids to it")
    @POST
    public GroupDTO.ReadGroupDTO create(@Valid GroupDTO.CreateGroupDTO toCreate) {
        return GroupDTOMapper.map(groupService.create(toCreate));
    }

    @Operation(summary = "Adds the person with personId to the group with id")
    @Path("/{id}/person/{personId}")
    @PUT
    public GroupDTO.ReadGroupDTO addPerson(@PathParam("id") Long id, @PathParam("personId") Long personId) {
        return GroupDTOMapper.map(groupService.addPerson(id, personId));
    }

    @Operation(summary = "Returns all groups and their associated persons")
    @GET
    public List<GroupDTO.ReadGroupDTO> getAll() {
        return GroupDTOMapper.map(groupService.getAll());
    }

    @Operation(summary = "Returns the group with the specified id and its associated persons")
    @Path("/{id}")
    @GET
    public GroupDTO.ReadGroupDTO getById(@PathParam("id") Long id) {
        return GroupDTOMapper.map(groupService.get(id));
    }

    @Operation(summary = "Deletes the group with the specified id")
    @Path("/{id}")
    @DELETE
    public void deleteById(@PathParam("id") Long id) {
        groupService.deleteById(id);
    }
}
