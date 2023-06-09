package com.switchfully.eurder.order.service;

import com.switchfully.eurder.item.domain.ItemRepository;
import com.switchfully.eurder.item.service.dtos.ItemDto;
import com.switchfully.eurder.item.service.mappers.ItemMapper;
import com.switchfully.eurder.order.domain.ItemGroups;
import com.switchfully.eurder.order.domain.Order;
import com.switchfully.eurder.order.domain.OrderRepository;
import com.switchfully.eurder.order.exceptions.ItemNotFoundException;
import com.switchfully.eurder.order.service.dtos.OrderDto;
import com.switchfully.eurder.order.service.dtos.UserInputOrderDto;
import com.switchfully.eurder.order.service.mappers.OrderMapper;
import com.switchfully.eurder.user.domain.Role;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.exceptions.UnauthorizedEndPointException;
import com.switchfully.eurder.user.service.CustomerService;
import com.switchfully.eurder.user.service.mappers.CustomerMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final CustomerService customerService;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, ItemRepository itemRepository,
                        ItemMapper itemMapper, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.customerService = customerService;
    }

    public OrderDto saveOrder(List<UserInputOrderDto> userInputOrderDto, String userId){
        //Check if allowed (= customer)
        customerService.checkIfValidUuidFormat(userId);
        customerService.checkIfUuidExists(userId);
        customerService.checkIfCustomer(userId);

        //Check that item exists
        checkIfItemExists(userInputOrderDto);

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

    public String checkIfItemExists(List<UserInputOrderDto> userInput){
        if(!itemRepository.getAllItemsNames()
                .containsAll(userInput.stream().map(ui -> ui.getItemName()).collect(Collectors.toList()))){
            throw new ItemNotFoundException();
        }
        return "Item(s) Found";
    }
}
