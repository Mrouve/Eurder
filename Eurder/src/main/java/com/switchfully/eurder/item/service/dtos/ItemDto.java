package com.switchfully.eurder.item.service.dtos;

import java.util.UUID;

public class ItemDto {
    private final UUID itemUuid;
    private String itemName;
    private String itemDescription;
    private int itemInStock;

    public ItemDto(UUID itemUuid, String itemName, String itemDescription, int itemInStock) {
        this.itemUuid = itemUuid;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
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
}
