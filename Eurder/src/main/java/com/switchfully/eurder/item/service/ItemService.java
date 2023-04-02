package com.switchfully.eurder.item.service;

import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.domain.ItemRepository;
import com.switchfully.eurder.item.exceptions.ItemAlreadyExistsException;
import com.switchfully.eurder.item.service.dtos.CreateItemDto;
import com.switchfully.eurder.item.service.dtos.ItemDto;
import com.switchfully.eurder.item.service.mappers.ItemMapper;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.exceptions.UnauthorizedEndPointException;
import com.switchfully.eurder.user.service.CustomerService;
import com.switchfully.eurder.user.service.CustomerService.*;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final CustomerService customerService;
    private final UserRepository userRepository;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper, CustomerService customerService, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.customerService = customerService;
        this.userRepository = userRepository;
    }

    public ItemDto saveItem(CreateItemDto createItemDto, String userId){
        customerService.checkIfValidUuidFormat(userId);
        customerService.checkIfUuidExists(userId);
        customerService.checkIfAdmin(userId);

        checkIfNameExists(createItemDto, "create");

        Item newItem = itemMapper.fromDto(createItemDto);
        Item ItemToSave = itemRepository.saveItem(newItem);
        return itemMapper.toDto(ItemToSave);
    }

    public ItemDto updateItem(CreateItemDto updateItemDto, String userId){
        customerService.checkIfValidUuidFormat(userId);
        customerService.checkIfUuidExists(userId);
        customerService.checkIfAdmin(userId);
;
        checkIfNameExists(updateItemDto, "update");

        ItemDto existingItemToUpdate = itemMapper.toDto(itemRepository.getItemsByUuid().values().stream()
                .filter(i -> i.getItemName().equals(updateItemDto.getItemName()))
                .findFirst()
                .orElseThrow());

        existingItemToUpdate.setItemName(updateItemDto.getItemName());
        existingItemToUpdate.setItemDescription(updateItemDto.getItemDescription());
        existingItemToUpdate.setItemPrice(updateItemDto.getItemPrice());
        existingItemToUpdate.setItemInStock(updateItemDto.getItemInStock());

        Item itemToUpdate = itemMapper.fromDtoWithUuid(existingItemToUpdate);
        Item itemUpdated = itemRepository.updateItem(itemToUpdate);
        return itemMapper.toDto(itemUpdated);
    }

    public String checkIfNameExists(CreateItemDto createItemDto, String actionToPerform){
        if(itemRepository.getAllItemsNames().contains(createItemDto.getItemName()) && actionToPerform.equals("create")){
            throw new ItemAlreadyExistsException();
        }
        return "No Exception, lets continue";
    }


}
