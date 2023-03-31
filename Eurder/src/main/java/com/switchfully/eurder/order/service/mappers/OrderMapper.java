package com.switchfully.eurder.order.service.mappers;

import com.switchfully.eurder.item.service.dtos.ItemDto;
import com.switchfully.eurder.order.domain.ItemGroups;
import com.switchfully.eurder.order.domain.Order;
import com.switchfully.eurder.order.service.dtos.OrderDto;
import com.switchfully.eurder.order.service.dtos.UserInputOrderDto;
import com.switchfully.eurder.user.domain.Customer;
import com.switchfully.eurder.user.service.dtos.CustomerDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public ItemGroups createOneItemGroup(UserInputOrderDto userInput, ItemDto orderedItems){
        ItemGroups itemGroup = new ItemGroups.ItemGroupsBuilder()
                .withItemId(orderedItems.getItemUuid())
                .withQuantity(userInput.getQuantity())
                .withCurrentPrice(orderedItems.getItemPrice())
                .withShipmentDate(orderedItems.getItemInStock())
                .build();
        return itemGroup;
    }

    public List<ItemGroups> listItemGroupsToDto(List<UserInputOrderDto> userInput, List<ItemDto> orderedItems){

        List<ItemGroups> ItemGroupsForCurrentOrder = new ArrayList<>();

        for(int i=0;i<userInput.size();i++){
            UserInputOrderDto currentUserInput = userInput.get(i);
            ItemDto correspondingItemDto = orderedItems.stream()
                    .filter(oi -> oi.getItemName().equals(currentUserInput.getItemName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No data found"));

            ItemGroupsForCurrentOrder.add(createOneItemGroup(currentUserInput, correspondingItemDto));
        }
        return ItemGroupsForCurrentOrder;
    }

    public Order createOrder(List<ItemGroups> orderItemGroups, String userId){
        double getTotalPrice = orderItemGroups.stream()
                .mapToDouble(oig -> oig.getPriceAtTimeOfOrder())
                .sum();

        return new Order(
                LocalDate.now(),
                getTotalPrice,
                UUID.fromString(userId),
                orderItemGroups
                );
    }

    public OrderDto toDto (Order order){
        return new OrderDto(order.getOrderUuid(), order.getOrderDate(), order.getTotalPrice(), order.getCustomerId(), order.getItemGroups());
    }
}
