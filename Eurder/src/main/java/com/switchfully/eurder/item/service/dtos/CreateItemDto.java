package com.switchfully.eurder.item.service.dtos;

import java.util.UUID;

public class CreateItemDto {
    private String itemName;
    private String itemDescription;
    private int itemInStock;

    public CreateItemDto(String itemName, String itemDescription, int itemInStock) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
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
}
