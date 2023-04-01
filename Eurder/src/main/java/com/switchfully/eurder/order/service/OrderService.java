package com.switchfully.eurder.order.service;

import com.switchfully.eurder.item.domain.ItemRepository;
import com.switchfully.eurder.item.service.dtos.ItemDto;
import com.switchfully.eurder.item.service.mappers.ItemMapper;
import com.switchfully.eurder.order.domain.ItemGroups;
import com.switchfully.eurder.order.domain.Order;
import com.switchfully.eurder.order.domain.OrderRepository;
import com.switchfully.eurder.order.service.dtos.OrderDto;
import com.switchfully.eurder.order.service.dtos.UserInputOrderDto;
import com.switchfully.eurder.order.service.mappers.OrderMapper;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.service.mappers.CustomerMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Collection;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, ItemRepository itemRepository, ItemMapper itemMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public OrderDto saveOrder(List<UserInputOrderDto> userInputOrderDto, String userId){
        //Check if allowed (= customer)

        // go get the item info + edit stock ; return item dto
        List<ItemDto> orderedItems = fetchDesiredItems(userInputOrderDto);

        // create the item groups dtos
        List<ItemGroups> orderItemGroups = createItemGroups(userInputOrderDto, orderedItems);

        // create New Order
        Order newOrder = orderMapper.createOrder(orderItemGroups, userId);

        // save Order
        Order orderToSave = orderRepository.saveOrder(newOrder);
        return orderMapper.toDto(orderToSave);
    }

    public List<ItemDto> fetchDesiredItems(List<UserInputOrderDto> userInputOrderDto){
        List<ItemDto> desiredItemsAsDto =  itemMapper.ListToDto(itemRepository.fetchDesiredItems(userInputOrderDto));
        return desiredItemsAsDto;
    }

    public List<ItemGroups> createItemGroups(List<UserInputOrderDto> userInput, List<ItemDto> orderedItems){
        return orderMapper.listItemGroupsToDto(userInput, orderedItems);
    }
}
