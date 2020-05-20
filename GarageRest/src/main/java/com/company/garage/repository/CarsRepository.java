package com.company.garage.repository;

import com.company.garage.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CarsRepository {
    private static final Logger LOGGER = Logger.getLogger(CarsRepository.class.getName());
    //Singleton Pattern
    private static CarsRepository instance;
    private List<Car> cars;
    private CarsRepository() {
        cars = new ArrayList<>();
    }
    public static void init() {
        instance = new CarsRepository();
        instance.cars.add(new Car(instance.getNewCarId(), "bmw", true, 1800));
        instance.cars.add(new Car(instance.getNewCarId(), "bmw2", false, 1800));
        instance.cars.add(new Car(instance.getNewCarId(), "bmw3", true, 1800));
        instance.cars.add(new Car(instance.getNewCarId(), "bmw4", true, 1800));
    }
    public static CarsRepository getInstance() {
        if (instance == null)
            init();
        return instance;
    }
    //Utility method to auto-increment the id
    private long getNewCarId() {
        return cars.size() + 1;
    }
    //END Singleton



    /** CRUD OPERATIONS
     * CREATE -> add
     * READ -> get
     * UPDATE -> get -> edit
     * DELETE -> remove
     * */
    //READ LIST
    public List<Car> getCars() {
        LOGGER.info("Retrieving List of Cars");
        return cars;
    }

    //READ UNIQUE
    public Car getCar(long carid) {
        LOGGER.info("Searching for car with id: " + carid);
        for (Car car : cars) {
            if (carid == car.getCarId())
                return car;
        }
        LOGGER.info("Car with id: " + carid + " Not Found");
        return null;
    }

    //CREATE
    public Car createCar(Car car) {
        LOGGER.info("Creating car: " + car.toString());
        car.setCarId(getNewCarId());
        cars.add(car);
        return car;
    }

    //UPDATE
    public Car updateCar(long carid, Car car) {
        LOGGER.info("Updating car with id: " + carid);
        for (Car car1 : cars) {
            if (carid == car1.getCarId()) {
                cars.remove(car1);
                car.setCarId(car1.getCarId());
                cars.add(car);
                return car;
            }
        }
        LOGGER.info("Car with id: " + carid + " Not Found");
        return null;
    }

    //DELETE
    public Car deleteCar(long carid) {
        LOGGER.info("Deleting car with id: " + carid);
        for (Car car : cars) {
            if (carid == car.getCarId()) {
                cars.remove(car);
                return car;
            }
        }
        LOGGER.info("Car with id: " + carid + " Not Found");
        return null;
    }

}
