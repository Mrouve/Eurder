package com.switchfully.eurder.order.domain;

import com.switchfully.eurder.user.domain.Customer;

import java.time.LocalDate;
import java.util.UUID;

public class ItemGroups {
    private UUID itemId;
    private int quantity;
    private double priceAtTimeOfOrder;
    private LocalDate shipmentDate;

    public ItemGroups(UUID itemId, int quantity, double priceAtTimeOfOrder, LocalDate shipmentDate) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.priceAtTimeOfOrder = priceAtTimeOfOrder;
        this.shipmentDate = shipmentDate;
    }

    //BUILDER===========================================================================================================
    public static class ItemGroupsBuilder{
        private UUID itemId;
        private int quantity;
        private double priceAtTimeOfOrder;
        private LocalDate shipmentDate;

        public ItemGroupsBuilder withItemId(UUID itemId){
            this.itemId = itemId;
            return this;
        }

        public ItemGroupsBuilder withQuantity(int quantity){
            this.quantity = quantity;
            return this;
        }

        public ItemGroupsBuilder withCurrentPrice(double priceAtTimeOfOrder){
            this.priceAtTimeOfOrder = priceAtTimeOfOrder;
            return this;
        }

        public ItemGroupsBuilder withShipmentDate(int stock){
            if(stock > 0){
                this.shipmentDate = LocalDate.now().plusDays(1);
                return this;
            }
            this.shipmentDate = LocalDate.now().plusDays(7);
            return this;
        }

        public ItemGroups build(){
            return new ItemGroups(itemId, quantity, priceAtTimeOfOrder, shipmentDate);
        }

    }

    //GETTERS===========================================================================================================
    public UUID getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPriceAtTimeOfOrder() {
        return priceAtTimeOfOrder;
    }

    public LocalDate getShipmentDate() {
        return shipmentDate;
    }
}
