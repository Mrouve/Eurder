package com.switchfully.eurder.order.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private final UUID orderUuid;
    private LocalDate orderDate;
    private double totalPrice;
    private UUID customerId;
    private List<ItemGroups> itemGroups;

    public Order(LocalDate orderDate, double totalPrice, UUID customerId, List<ItemGroups> itemGroups) {
        this.orderUuid = UUID.randomUUID();
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.customerId = customerId;
        this.itemGroups = itemGroups;
    }

    public UUID getOrderUuid() {
        return orderUuid;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public List<ItemGroups> getItemGroups() {
        return itemGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.totalPrice, totalPrice) == 0 && Objects.equals(orderUuid, order.orderUuid) && Objects.equals(orderDate, order.orderDate) && Objects.equals(customerId, order.customerId) && Objects.equals(itemGroups, order.itemGroups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderUuid, orderDate, totalPrice, customerId, itemGroups);
    }
}
