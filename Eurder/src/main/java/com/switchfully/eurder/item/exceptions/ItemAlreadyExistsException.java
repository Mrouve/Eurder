package com.switchfully.eurder.item.exceptions;

public class ItemAlreadyExistsException extends RuntimeException{
    public ItemAlreadyExistsException(){super("An item with the exact same name already exists");}
}
