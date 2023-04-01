package com.switchfully.eurder.order.service.dtos;

import java.time.LocalDate;
import java.util.UUID;

public class CreateItemGroupsDto {
    private UUID itemId;
    private int quantity;
    private double priceAtTimeOfOrder;

    public CreateItemGroupsDto(UUID itemId, int quantity, double priceAtTimeOfOrder, LocalDate shipmentDate) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public UUID getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

}
