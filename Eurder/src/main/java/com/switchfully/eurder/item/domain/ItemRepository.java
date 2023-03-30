package com.switchfully.eurder.item.domain;

import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
}
