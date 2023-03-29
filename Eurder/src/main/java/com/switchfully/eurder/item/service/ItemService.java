package com.switchfully.eurder.item.service;

import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.domain.ItemRepository;
import com.switchfully.eurder.item.service.dtos.CreateItemDto;
import com.switchfully.eurder.item.service.dtos.ItemDto;
import com.switchfully.eurder.item.service.mappers.ItemMapper;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public ItemDto saveItem(CreateItemDto createItemDto){
        checkIfAdmin();
        Item newItem = itemMapper.fromDto(createItemDto);
        Item ItemToSave = itemRepository.saveItem(newItem);
        return itemMapper.toDto(ItemToSave);
    }

    public void checkIfAdmin(){
        //TO DO
        // throw new UnauthorizedEndPointException();
    }
}
