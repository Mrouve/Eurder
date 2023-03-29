package com.switchfully.eurder.item.domain;

import org.springframework.context.annotation.EnableMBeanExport;

import java.util.Objects;
import java.util.UUID;

public class Item {
    private final UUID itemUuid;
    private String itemName;
    private String itemDescription;
    private int itemInStock;

    public Item(String itemName, String itemDescription, int itemInStock) {
        this.itemUuid = UUID.randomUUID();
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemInStock = itemInStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemInStock == item.itemInStock && Objects.equals(itemUuid, item.itemUuid) && Objects.equals(itemName, item.itemName) && Objects.equals(itemDescription, item.itemDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemUuid, itemName, itemDescription, itemInStock);
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
}
