package com.switchfully.eurder.customer.exceptions;

public class InvalidUuidException extends RuntimeException {
    public InvalidUuidException(){
        super("This Unique Id does not exists");
    }
}
