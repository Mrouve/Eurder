package com.switchfully.eurder.user.exceptions;

public class IncompleteAddressException extends RuntimeException{
    public IncompleteAddressException(){
        super("The address is not complete");
    }
}
