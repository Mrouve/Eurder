package com.switchfully.eurder.user.exceptions;

public class InvalidCustomerFieldFormatException extends RuntimeException {
    public InvalidCustomerFieldFormatException(){
        super("This phone number is too short to be of any valid format");
    }
}
