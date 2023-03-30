package com.switchfully.eurder.order.service.dtos;

import java.time.LocalDate;
import java.util.UUID;

public class ItemGroupsDto {
    private UUID itemId;
    private int quantity;
    private double priceAtTimeOfOrder;
    private LocalDate shipmentDate;

    public ItemGroupsDto(UUID itemId, int quantity, double priceAtTimeOfOrder, LocalDate shipmentDate) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.priceAtTimeOfOrder = priceAtTimeOfOrder;
        this.shipmentDate = shipmentDate;
    }
}
