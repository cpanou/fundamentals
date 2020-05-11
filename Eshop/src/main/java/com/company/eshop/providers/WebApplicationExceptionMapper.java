package com.company.eshop.providers;


import com.company.eshop.dtos.ErrorMessage;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
//(Exception) - The Provider annotation informs the JAX-RS Runtime that it extends a default runtime behavior.
//Since it implements the ExceptionMapper interface the runtime will handle exceptions of the specified type.
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException e) {
        if (e instanceof NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON)
                    .entity(new ErrorMessage("14", " The requested uri was not found."))
                    .build();
        } else if (e instanceof NotAllowedException) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).type(MediaType.APPLICATION_JSON)
                    .entity(new ErrorMessage("11", " The requested method is not supported"))
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON)
                .entity(new ErrorMessage("300", "Something Went Wrong!"))
                .build();
    }

}
