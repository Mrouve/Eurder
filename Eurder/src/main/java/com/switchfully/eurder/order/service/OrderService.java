package com.switchfully.eurder.order.service;

import com.switchfully.eurder.item.domain.ItemRepository;
import com.switchfully.eurder.item.service.dtos.ItemDto;
import com.switchfully.eurder.item.service.mappers.ItemMapper;
import com.switchfully.eurder.order.domain.Order;
import com.switchfully.eurder.order.domain.OrderRepository;
import com.switchfully.eurder.order.service.dtos.UserInputOrderDto;
import com.switchfully.eurder.order.service.mappers.OrderMapper;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.service.mappers.CustomerMapper;
import org.springframework.stereotype.Service;

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

    public Order saveOrder(List<UserInputOrderDto> userInputOrderDto){
        // go get the item info + edit stock ; return item dto
        List<ItemDto> orderedItems = fetchDesiredItems(userInputOrderDto);

        //
    }

    public List<ItemDto> fetchDesiredItems(List<UserInputOrderDto> userInputOrderDto){
        List<ItemDto> desiredItemsAsDto =  itemMapper.ListToDto(itemRepository.fetchDesiredItems(userInputOrderDto));
        return desiredItemsAsDto;
    }
}
