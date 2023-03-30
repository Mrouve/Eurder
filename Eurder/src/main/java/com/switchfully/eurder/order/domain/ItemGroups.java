package com.switchfully.eurder.order.domain;

import java.time.LocalDate;
import java.util.UUID;

public class ItemGroups {
    private UUID itemId;
    private int quantity;
    private double priceAtTimeOfOrder;
    //private LocalDate shipmentDate;

    public ItemGroups(UUID itemId, int quantity, double priceAtTimeOfOrder) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.priceAtTimeOfOrder = priceAtTimeOfOrder;
        //this.shipmentDate = defineShipmentDate();
    }


}
