package com.company.eshop.controllers;

import com.company.eshop.dtos.*;
import com.company.eshop.exceptions.ApplicationError;
import com.company.eshop.exceptions.ApplicationException;
import com.company.eshop.model.Product;
import com.company.eshop.model.User;
import com.company.eshop.services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@Path("/users")
public class UserResource {
    private static final Logger log = Logger.getLogger(UserResource.class.getSimpleName());

    private UserService service = new UserService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        log.info("get All Users Invoked ");
        return Response.ok(service.getUsers())
                .build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(CreateUserRequest createUserRequest) {
        log.info("create User Invoked : " + createUserRequest.getUsername() );

        String emailPattern = "[\\w-]+@([\\w-]+\\.)+[\\w-]+";
        if( ! Pattern.matches(emailPattern, createUserRequest.getEmail()) )
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage( "1", "Email not valid!"))
                    .build();

        return Response.ok(service.addUser(createUserRequest)).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAllUsers() {
        log.info("delete All Users Invoked ");
        return Response.ok(service.deleteAllUsers())
                .build();
    }

    @GET
    @Path("/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userid") long UserId) {
        log.info("retrieve User Invoked : " + UserId);
        return Response.ok(service.getUser(UserId))
                .build();
    }

    @GET
    @Path("/{userid}/checkout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkout(@PathParam("userid") long id) {
        log.info("User with id " + id + " checkout invoked");
        return Response.ok(service.checkout(id))
                .build();
    }


    // Sub-Resource Locator Method
    // When Any request contains the /cart in the path the execution is delegated to the CartResource class
    @Path("/{userid}/cart")
    @Produces(MediaType.APPLICATION_JSON)
    public CartResource getUserCart(@PathParam("userid") long UserId) {
        log.info("retrieve User Invoked : " + UserId);
        GetUserResponse user = service.getUser(UserId);
        return new CartResource( user );
    }


}
