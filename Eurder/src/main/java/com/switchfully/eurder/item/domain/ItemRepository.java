package com.switchfully.eurder.item.domain;

import com.switchfully.eurder.item.service.dtos.ItemDto;
import com.switchfully.eurder.order.service.dtos.UserInputOrderDto;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class ItemRepository {

    private final ConcurrentHashMap<UUID, Item> itemsByUuid;
    public ItemRepository(){
        this.itemsByUuid = new ConcurrentHashMap<>();
    }

    public Item saveItem(Item item){
        itemsByUuid.put(item.getItemUuid(), item);
        return item;
    }

    public ConcurrentHashMap<UUID, Item> getItemsByUuid() {
        return itemsByUuid;
    }

    public List<Item> fetchDesiredItems(List<UserInputOrderDto> userInputOrderDto){

        //For each, check if name correspond to an item

        List<String> itemNamesInUserInput = userInputOrderDto.stream()
                .map(UserInputOrderDto::getItemName)
                        .toList();

        List<Item> orderedItems = itemsByUuid.values().stream()
                .filter(ui -> itemNamesInUserInput.contains(ui.getItemName()))
                        .collect(Collectors.toList());

//        List<Item> orderedItems = itemsByUuid.values().stream()
//                .filter(i -> userInputOrderDto.stream()
//                        .anyMatch(ui -> ui.getItemName().equals(i.getItemName())))
//                .collect(Collectors.toList());

        editStock(orderedItems, userInputOrderDto);
        return orderedItems;
    }

    public List<Item> editStock(List<Item> orderedItems, List<UserInputOrderDto> userInput) {
//        return orderedItems.stream()
//                .forEach(i -> userInput.stream()
//                        .forEach(ui -> {
//                                    if (ui.getItemName().equals(i.getItemName())){
//                                        i.setItemInStock(i.getItemInStock() - ui.getQuantity());
//                                    }
//                                }
//                        ));

        for (int i = 0; i < orderedItems.size(); i++) {
            for (int j = 0; j < userInput.size(); j++) {
                if (orderedItems.get(i).getItemName().equals(userInput.get(j).getItemName())) {
                    orderedItems.get(i).setItemInStock(orderedItems.get(i).getItemInStock() - userInput.get(j).getQuantity());
                }
            }
        }
        return orderedItems;
    }

    public List<String> getAllItemsNames(){
        return itemsByUuid.values().stream()
                .map(i -> i.getItemName())
                .collect(Collectors.toList());
    }

}
