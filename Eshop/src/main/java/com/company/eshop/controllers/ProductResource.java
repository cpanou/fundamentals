package com.company.eshop.controllers;

import com.company.eshop.application.MyApplication;
import com.company.eshop.model.Product;
import com.company.eshop.repository.ProductRepository;
import com.company.eshop.services.ProductService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {
    private static final Logger log = Logger.getLogger(ProductResource.class.getSimpleName());

    //Gets a Reference to the service
    private ProductService service = new ProductService();

    @GET
    public Response getProducts() {
        log.info("get All products Invoked ");
        return Response.ok(service.getProducts())
                .build();
    }

    @POST
    public Response createProduct(Product product) {
        log.info("creating product " + product.getProductName() + " invoked");
        return  Response.ok(service.createProduct(product))
                .build();
    }

    @GET
    @Path("/{productId}")
    public Response getProduct(@PathParam("productId")long productId) {
        log.info("get product with id " + productId + " invoked");
        return Response.ok(service.getProduct(productId))
                .build();
    }


}
