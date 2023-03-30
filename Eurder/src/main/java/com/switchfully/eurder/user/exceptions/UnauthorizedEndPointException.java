package com.switchfully.eurder.user.exceptions;

public class UnauthorizedEndPointException extends RuntimeException {
    public UnauthorizedEndPointException(){
        super("Unauthorized End Point !");
    }
}
