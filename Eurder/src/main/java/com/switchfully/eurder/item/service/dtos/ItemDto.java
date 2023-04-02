package com.switchfully.eurder.item.service.dtos;

import java.util.UUID;

public class ItemDto {
    private final UUID itemUuid;
    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private int itemInStock;

    public ItemDto(UUID itemUuid, String itemName, String itemDescription, double itemPrice, int itemInStock) {
        this.itemUuid = itemUuid;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemInStock = itemInStock;
    }

    public UUID getItemUuid() {
        return itemUuid;
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

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemInStock(int itemInStock) {
        this.itemInStock = itemInStock;
    }
}
