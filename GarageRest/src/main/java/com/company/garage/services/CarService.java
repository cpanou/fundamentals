package com.company.garage.services;

import com.company.garage.repository.CarsRepository;
import com.company.garage.model.Car;

import java.util.List;
import java.util.logging.Logger;

public class CarService {
    private CarsRepository repository = CarsRepository.getInstance();

    public CarService() {
    }

    public List<Car> getCars() {
        return repository.getCars();
    }

    public Car getCar(long carId) {
        return repository.getCar(carId);
    }

    public Car createCar(Car car) {
        return repository.createCar(car);
    }

    public Car updateCar(long carId, Car car) {
        return repository.updateCar(carId, car);
    }

    public Car deleteCar(long carId) {
        return repository.deleteCar(carId);
    }

}
