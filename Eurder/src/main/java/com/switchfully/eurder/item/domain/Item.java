package com.switchfully.eurder.item.domain;

import org.springframework.context.annotation.EnableMBeanExport;

import java.util.Objects;
import java.util.UUID;

public class Item {
    private final UUID itemUuid;
    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private int itemInStock;

    public Item(String itemName, String itemDescription, double itemPrice, int itemInStock) {
        this.itemUuid = UUID.randomUUID();
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemInStock = itemInStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(item.itemPrice, itemPrice) == 0 && itemInStock == item.itemInStock && Objects.equals(itemUuid, item.itemUuid) && Objects.equals(itemName, item.itemName) && Objects.equals(itemDescription, item.itemDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemUuid, itemName, itemDescription, itemPrice, itemInStock);
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

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemInStock(int itemInStock) {
        this.itemInStock = itemInStock;
    }
}
