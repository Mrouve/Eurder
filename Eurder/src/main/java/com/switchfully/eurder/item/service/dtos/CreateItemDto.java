package com.switchfully.eurder.item.service.dtos;

import java.util.UUID;

public class CreateItemDto {
    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private int itemInStock;

    public CreateItemDto(String itemName, String itemDescription, double itemPrice, int itemInStock) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemInStock = itemInStock;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getItemInStock() {
        return itemInStock;
    }

    public double getItemPrice() {
        return itemPrice;
    }
}
