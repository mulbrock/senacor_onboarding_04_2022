package com.senacor.onboardingbackend.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WasDeletedExceptionHandler implements ExceptionMapper<WasDeletedException> {

    @Override
    public Response toResponse(WasDeletedException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    }
}
