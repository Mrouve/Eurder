package com.switchfully.eurder.order.service.dtos;

import com.switchfully.eurder.order.domain.ItemGroups;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class OrderDto {
    private final UUID orderUuid;
    private LocalDate orderDate;
    private double totalPrice;
    private UUID customerId;
    private List<ItemGroups> itemGroups;

    public OrderDto(UUID orderUuid, LocalDate orderDate, double totalPrice, UUID customerId, List<ItemGroups> itemGroups) {
        this.orderUuid = orderUuid;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.customerId = customerId;
        this.itemGroups = itemGroups;
    }
}
