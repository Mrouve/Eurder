package com.switchfully.eurder.item.domain;

import com.switchfully.eurder.item.service.ItemService;
import com.switchfully.eurder.order.service.dtos.UserInputOrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemRepositoryTest {

    private ItemRepository itemRepository = new ItemRepository();
    @Autowired
    private ItemService itemService ;
    private Item item1;
    private Item item2;

    @BeforeEach
    void setup(){
        itemRepository = new ItemRepository();
        item1 = new Item("name1", "descr1", 10.00, 1);
        item2 = new Item("name2", "descr2", 20.00, 2);
        itemRepository.saveItem(item1);
        itemRepository.saveItem(item2);
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

    @Test
    void fetchDesiredItems_givenAListOfUserInputOrder_thenReturnListOfCorrespondingItems(){
        //Given
        List<UserInputOrderDto> userInputOrder = new ArrayList<>();
        UserInputOrderDto itemInOrder1 = new UserInputOrderDto("name1", 1);
        UserInputOrderDto itemInOrder2 = new UserInputOrderDto("name2", 1);
        userInputOrder.add(itemInOrder1);
        userInputOrder.add(itemInOrder2);

        //When
        List<Item> listItemsInOrder = itemRepository.fetchDesiredItems(userInputOrder);

        //Then
        assertTrue(listItemsInOrder.contains(item1));
        assertTrue(listItemsInOrder.contains(item2));
        assertTrue(listItemsInOrder.size() == 2);
    }

    @Test
    void editStock_givenAListOfItemsAndQuantityOrdered_thenReturnAListOfCorrespondingItemsWithTheirQuantityInStockUpdated(){
        //Given
        List<UserInputOrderDto> userInputOrder = new ArrayList<>();
        UserInputOrderDto itemInOrder1 = new UserInputOrderDto("name1", 1);
        UserInputOrderDto itemInOrder2 = new UserInputOrderDto("name2", 1);
        userInputOrder.add(itemInOrder1);
        userInputOrder.add(itemInOrder2);

        List<Item> itemListToUpdate = new ArrayList<>();
        itemListToUpdate.add(item1);
        itemListToUpdate.add(item2);

        int item1OriginalStock = item1.getItemInStock();
        int item2OriginalStock = item2.getItemInStock();
        int item1OrderedQuantity = itemInOrder1.getQuantity();
        int item2OrderedQuantity = itemInOrder2.getQuantity();

        //When
        List<Item> itemListUpdated = itemRepository.editStock(itemListToUpdate,userInputOrder);

        //Then
        assertEquals(item1.getItemInStock(), (item1OriginalStock - item1OrderedQuantity));
        assertEquals(item2.getItemInStock(), (item2OriginalStock - item2OrderedQuantity));
    }
}