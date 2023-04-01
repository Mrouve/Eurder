package com.switchfully.eurder.order.exceptions;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(){super("One or more desired items are not in the database");}
}
