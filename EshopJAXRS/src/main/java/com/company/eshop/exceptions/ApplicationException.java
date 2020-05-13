package com.company.eshop.exceptions;

public class ApplicationException extends RuntimeException {

    private ApplicationError error;

    public ApplicationException(ApplicationError error){
        this.error = error;
    }

    public ApplicationError getError() {
        return error;
    }

    public void setError(ApplicationError error) {
        this.error = error;
    }
}
