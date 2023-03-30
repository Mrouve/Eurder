package com.switchfully.eurder.item.domain;

import com.switchfully.eurder.item.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemRepositoryTest {

    private ItemRepository itemRepository = new ItemRepository();
    @Autowired
    private ItemService itemService ;
    private Item item1;

    @BeforeEach
    void setup(){
        itemRepository = new ItemRepository();
        item1 = new Item("name1", "descr1", 10.00, 2);
        itemRepository.saveItem(item1);
    }


    @Test
    void saveItem_givenAnItemToSave_thenSavedItemIsContainedInRepo() {
        //Given
        Item itemToSave = item1;

        //When
        Item savedItem = itemRepository.saveItem(itemToSave);

        //Then
        assertTrue(itemRepository.getItemsByUuid().containsValue(savedItem));
    }
}