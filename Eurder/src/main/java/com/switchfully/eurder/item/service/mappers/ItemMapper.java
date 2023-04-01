package com.switchfully.eurder.item.service.mappers;

import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.service.dtos.CreateItemDto;
import com.switchfully.eurder.item.service.dtos.ItemDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemMapper {
    public Item fromDto(CreateItemDto createItemDto){
        return new Item(createItemDto.getItemName(), createItemDto.getItemDescription(), createItemDto.getItemPrice(),createItemDto.getItemInStock());
    }

    public ItemDto toDto(Item item){
        return new ItemDto(item.getItemUuid(), item.getItemName(), item.getItemDescription(), item.getItemPrice(), item.getItemInStock());
    }

    public List<ItemDto> ListToDto(List<Item> listOfItems){
        return listOfItems
                .stream()
                .map(this::toDto)
                .toList();
    }
}
