package com.company.eshop.controllers;

import com.company.eshop.dtos.GetUserResponse;
import com.company.eshop.model.Product;
import com.company.eshop.services.CartService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;


//Sub-resource class
public class CartResource {
    private static final Logger log = Logger.getLogger(UserResource.class.getSimpleName());

    private CartService service = new CartService();

    private GetUserResponse user;

    public CartResource(GetUserResponse userResponse) {
        this.user = userResponse;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserCart() {
        log.info("Get User Cart invoked for user: "+ user.getUserId());
        return Response.ok(service.getUserCart(Long.parseLong(user.getUserId())))
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addToCart(Product product) {
        log.info("Add to Cart invoked for User: "+ user.getUserId() + " Product:" + product.getProductId());
        return Response.ok(service.addToCart(Long.parseLong(user.getUserId()), product))
                .build();
    }

    @DELETE
    @Path("/{productid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFromCart(@PathParam("productid") long productId) {
        log.info("Remove from Cart invoked for User: "+ user.getUserId() + " Product:" + productId);
        return Response.ok(service.deleteFromCart(Long.parseLong(user.getUserId()), productId))
                .build();
    }


}
