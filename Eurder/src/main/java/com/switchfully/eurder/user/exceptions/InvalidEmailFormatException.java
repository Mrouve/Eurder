package com.switchfully.eurder.user.exceptions;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException(String email){
        super("Invalid email: " + email);
    }
}
