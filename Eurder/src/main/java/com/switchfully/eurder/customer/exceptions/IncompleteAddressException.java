package com.switchfully.eurder.customer.exceptions;

public class IncompleteAddressException extends RuntimeException{
    public IncompleteAddressException(){
        super("The address is not complete");
    }
}
