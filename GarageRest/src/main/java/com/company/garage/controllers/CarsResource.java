package com.company.garage.controllers;


import com.company.garage.model.Car;
import com.company.garage.repository.CarsRepository;
import com.company.garage.services.CarService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

@Path("/cars")
public class CarsResource {
    /** HTTP METHODS TO CRUD OPERATIONS
    * POST -> Create
    * GET -> Read
    * PUT -> Update
    * DELETE -> Delete
    * */
    private static final Logger LOGGER = Logger.getLogger(CarsResource.class.getSimpleName());
    private CarService service  = new CarService();

    //HANDLES
    //GET http://localhost:8080/garage_war/garage/cars
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Car> getCars() {
        LOGGER.info("Get Cars Invoked");
        return service.getCars();
    }

    //HANDLES
    //POST http://localhost:8080/garage_war/garage/cars
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Car createCar( Car car ) {
        LOGGER.info("Post Car Invoked for car:" + car.toString());
        return service.createCar(car);
    }

    //HANDLES
    //GET http://localhost:8080/garage_war/garage/cars/{car-id}
    @GET
    @Path("/{carId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Car getCar( @PathParam("carId") long id ) {
        LOGGER.info("Get Car Invoked for id:" + id);
        return service.getCar(id);
    }

    //HANDLES
    //PUT http://localhost:8080/garage_war/garage/cars/{car-id}
    @PUT
    @Path("/{carId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Car editCar(  @PathParam("carId") long carid, Car car ) {
        LOGGER.info("Put Car Invoked for id:" + carid);
        return service.updateCar(carid, car);
    }

    //HANDLES
    //DELETE http://localhost:8080/garage_war/garage/cars/{car-id}
    @DELETE
    @Path("/{carId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Car deleteCar( @PathParam("carId") long carid ) {
        LOGGER.info("Delete Car Invoked for id:" + carid);
        return service.deleteCar(carid);
    }
}
