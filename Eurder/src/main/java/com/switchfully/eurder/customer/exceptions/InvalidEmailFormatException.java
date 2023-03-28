package com.switchfully.eurder.customer.exceptions;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException(String email){
        super("Invalid email: " + email);
    }
}
