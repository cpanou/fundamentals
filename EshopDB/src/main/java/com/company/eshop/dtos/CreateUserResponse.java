package com.company.eshop.dtos;

public class CreateUserResponse {
    private String userId;
    private String email;
    private String username;
    private String firstname;
    private String lastname;

    public CreateUserResponse(){}

    public CreateUserResponse(String userId, String username, String firstname, String lastname) {
        this.userId = userId;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    //Mapper constructor
    public CreateUserResponse(String userId, String username, String email, String firstname, String lastname) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
