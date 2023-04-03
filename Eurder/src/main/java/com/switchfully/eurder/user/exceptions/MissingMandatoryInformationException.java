package com.switchfully.eurder.user.exceptions;

public class MissingMandatoryInformationException extends RuntimeException{
    public MissingMandatoryInformationException(){super("A mandatory information is missing");}
}
