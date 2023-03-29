package com.switchfully.eurder.item.service;

import com.switchfully.eurder.customer.domain.Customer;
import com.switchfully.eurder.customer.domain.CustomerRepository;
import com.switchfully.eurder.customer.service.CustomerService;
import com.switchfully.eurder.customer.service.dtos.CreateCustomerDto;
import com.switchfully.eurder.customer.service.dtos.CustomerDto;
import com.switchfully.eurder.customer.service.mappers.CustomerMapper;
import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.domain.ItemRepository;
import com.switchfully.eurder.item.service.dtos.CreateItemDto;
import com.switchfully.eurder.item.service.dtos.ItemDto;
import com.switchfully.eurder.item.service.mappers.ItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {

    private ItemRepository itemRepository ;
    private ItemMapper itemMapper;
    private ItemService itemService;
    private Item item1;
    private Item item2;
    private CreateItemDto createItemDto;
    private static final int SIZE_SETUP = 2;

    @BeforeEach
    void setup(){
        itemRepository = new ItemRepository();
        itemMapper = new ItemMapper();
        itemService = new ItemService(itemRepository,itemMapper);
        createItemDto = new CreateItemDto("itemName1", "itemDescription1", 10.02, 4);
    }

    @Test
    void saveItem_givenAValidCreateItemDto_thenShouldBeReturnedAValidItemDto_CaseUserIsAdmin() {
        //Given
        ItemDto ItemToSaveDto = itemService.saveItem(createItemDto);

        //Then
        assertNotNull(ItemToSaveDto.getItemUuid());
        assertEquals(ItemToSaveDto.getItemName(), createItemDto.getItemName());
        assertEquals(ItemToSaveDto.getItemDescription(), createItemDto.getItemDescription());
        assertEquals(ItemToSaveDto.getItemPrice(), createItemDto.getItemPrice());
        assertEquals(ItemToSaveDto.getItemInStock(), createItemDto.getItemInStock());
    }
}