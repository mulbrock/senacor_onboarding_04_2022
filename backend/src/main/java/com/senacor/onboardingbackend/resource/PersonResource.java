package com.senacor.onboardingbackend.resource;

import com.senacor.onboardingbackend.datatransferobject.PersonDTO;
import com.senacor.onboardingbackend.datatransferobject.mapping.PersonDTOMapper;
import com.senacor.onboardingbackend.service.PersonService;
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

@Path("/api/v1/persons")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PersonResource {

    private PersonService personService;

    @Operation(summary = "Creates a new person")
    @POST
    public PersonDTO.ReadPersonDTO create(@Valid PersonDTO.CreateUpdatePersonDTO toCreate) {
        return PersonDTOMapper.map(personService.create(toCreate));
    }

    @Operation(summary = "Returns all persons and their groups")
    @GET
    public List<PersonDTO.ReadPersonDTO> getAll() {
        return PersonDTOMapper.map(personService.getAll());
    }

    @Operation(summary = "Returns the person with the specified id and their group")
    @Path("/{id}")
    @GET
    public PersonDTO.ReadPersonDTO getById(@PathParam("id") Long id) {
        return PersonDTOMapper.map(personService.get(id));
    }

    @Operation(summary = "Updates the person with the specified id")
    @Path("/{id}")
    @PUT
    public PersonDTO.ReadPersonDTO updateById(@PathParam("id") Long id, @Valid PersonDTO.CreateUpdatePersonDTO toUpdate) {
        return PersonDTOMapper.map(personService.updateById(id, toUpdate));
    }

    @Operation(summary = "Deletes the person with the specified id")
    @Path("/{id}")
    @DELETE
    public void deleteById(@PathParam("id") Long id) {
        personService.deleteById(id);
    }
}
