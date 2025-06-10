package com.tastytown.backend.exception;

public class CatagoryNotFoundException extends RuntimeException {

    public CatagoryNotFoundException(){
        super();
    }

    public CatagoryNotFoundException(String message) {
        super(message);
    }
    
}
