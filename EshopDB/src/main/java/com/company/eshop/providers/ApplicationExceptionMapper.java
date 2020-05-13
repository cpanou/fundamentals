package com.company.eshop.providers;

import com.company.eshop.exceptions.ApplicationError;
import com.company.eshop.exceptions.ApplicationException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {

    @Override
    public Response toResponse(ApplicationException e) {
        ApplicationError error = e.getError();
        return Response.status(error.getHttpStatus())
                .type(MediaType.APPLICATION_JSON)
                .entity(error.getError())
                .build();
    }

}
