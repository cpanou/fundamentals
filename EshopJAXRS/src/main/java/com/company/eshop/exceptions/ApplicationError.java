package com.company.eshop.exceptions;

import com.company.eshop.dtos.ErrorMessage;

import javax.ws.rs.core.Response;

public enum ApplicationError {
    USER_NOT_FOUND(new ErrorMessage("3", "User not found" ), Response.Status.NOT_FOUND),
    NO_USERS_FOUND(new ErrorMessage("4", "No Users Found"), Response.Status.NOT_FOUND),
    PRODUCT_NOT_FOUND(new ErrorMessage("5", "Product not found"), Response.Status.NOT_FOUND),
    PRODUCT_NOT_IN_CART(new ErrorMessage("6", "Product Not in Cart"), Response.Status.BAD_REQUEST),
    CART_EMPTY(new ErrorMessage("7", "Cart is empty"), Response.Status.BAD_REQUEST),
    USERNAME_EXISTS(new ErrorMessage("9", "Username Exists"), Response.Status.CONFLICT)
    ;

    private ErrorMessage error;
    private Response.Status httpStatus;

    ApplicationError(ErrorMessage error, Response.Status status){
        this.error = error;
        this.httpStatus = status;
    }

    public ErrorMessage getError() {
        return error;
    }

    public void setError(ErrorMessage error) {
        this.error = error;
    }

    public Response.Status getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Response.Status httpStatus) {
        this.httpStatus = httpStatus;
    }
}
