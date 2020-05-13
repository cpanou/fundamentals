package com.company.eshop.model;


import com.company.eshop.controllers.UserResource;

import java.util.logging.Logger;

public class User {
    private static final Logger log = Logger.getLogger(UserResource.class.getSimpleName());

    private long userId;

    private String email;
    private String username;
    private String firstName;
    private String lastName;

    private int ordersPlaced;
    private Cart cart;

    public User(){
        cart = new Cart();
    }

    public User(long userId, String email, String username, String firstname, String lastname ) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.firstName = firstname;
        this.lastName = lastname;
        this.cart = new Cart();
    }


    //Mapper constructor
    public User(String email, String username, String firstname, String lastname ) {
        this.email = email;
        this.username = username;
        this.firstName = firstname;
        this.lastName = lastname;
        this.cart = new Cart();
    }


    public User(long userId, String username, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cart = new Cart();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public double checkout() {
        double totalCost = 0.0;
        log.info("User " + this.getUsername() + " checkout called, Cart: " + cart.getProductList().toString());
        for(Product product: cart.getProductList()) {
            totalCost += product.buy();
        }
        cart.getProductList().clear();

        return totalCost;
    }

    public int getOrdersPlaced() {
        return ordersPlaced;
    }

    public void setOrdersPlaced(int ordersPlaced) {
        this.ordersPlaced = ordersPlaced;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
