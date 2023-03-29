package com.switchfully.eurder.item.service.mappers;

import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.service.dtos.CreateItemDto;
import com.switchfully.eurder.item.service.dtos.ItemDto;

public class ItemMapper {
    public Item fromDto(CreateItemDto createItemDto){
        return new Item(createItemDto.getItemName(), createItemDto.getItemDescription(), createItemDto.getItemInStock());
    }

    public ItemDto toDto(Item item){
        return new ItemDto(item.getItemUuid(), item.getItemName(), item.getItemDescription(), item.getItemInStock());
    }
}
