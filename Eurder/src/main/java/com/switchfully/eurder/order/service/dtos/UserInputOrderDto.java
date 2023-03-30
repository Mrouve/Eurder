package com.switchfully.eurder.order.service.dtos;

public class UserInputOrderDto {
    private String itemName;
    private int quantity;

    public UserInputOrderDto(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }
}
