package com.svalero.library.exception;

public class RentNotFoundException extends Exception{

    public RentNotFoundException() {
        super("Rent not found");
    }

    public RentNotFoundException(String message) {
        super(message);
    }
}

