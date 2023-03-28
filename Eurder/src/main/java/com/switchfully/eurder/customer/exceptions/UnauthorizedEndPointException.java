package com.switchfully.eurder.customer.exceptions;

public class UnauthorizedEndPointException extends RuntimeException {
    public UnauthorizedEndPointException(){
        super("Unauthorized End Point !");
    }
}
