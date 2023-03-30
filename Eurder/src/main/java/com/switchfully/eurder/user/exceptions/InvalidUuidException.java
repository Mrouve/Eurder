package com.switchfully.eurder.user.exceptions;

public class InvalidUuidException extends RuntimeException {
    public InvalidUuidException(){
        super("This Unique Id does not exists");
    }
}
