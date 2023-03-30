package com.switchfully.eurder.order.service.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CreateOrderDto {
    private LocalDate orderDate;
    private double totalPrice;
    private UUID customerId;
    private List<ItemGroupsDto> itemGroups;

    public CreateOrderDto(LocalDate orderDate, double totalPrice, UUID customerId, List<ItemGroupsDto> itemGroups) {
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.customerId = customerId;
        this.itemGroups = itemGroups;
    }
}
