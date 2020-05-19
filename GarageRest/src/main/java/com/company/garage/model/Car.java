package com.company.garage.model;

public class Car {
    private long carId;

    private String brand;
    private boolean damaged;
    private long cc;

    public Car(){}

    public Car(long carId, String brand, boolean damaged, long cc) {
        this.carId = carId;
        this.brand = brand;
        this.damaged = damaged;
        this.cc = cc;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public long getCc() {
        return cc;
    }

    public void setCc(long cc) {
        this.cc = cc;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public void repair(){
        this.damaged = false;
    }
}
