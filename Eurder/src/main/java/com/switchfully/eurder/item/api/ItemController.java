package com.switchfully.eurder.item.api;

import com.switchfully.eurder.item.service.ItemService;
import com.switchfully.eurder.item.service.dtos.CreateItemDto;
import com.switchfully.eurder.item.service.dtos.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/item")
public class ItemController {
    private final ItemService itemService;
    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    //POST=============================================================================================================
    @PostMapping(path="", consumes = "application/json", produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto create(@RequestBody CreateItemDto createItemDto){
        return itemService.saveItem(createItemDto);
    }



}
