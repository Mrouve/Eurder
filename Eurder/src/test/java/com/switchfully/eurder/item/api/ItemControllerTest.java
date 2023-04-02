package com.switchfully.eurder.item.api;

import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.domain.ItemRepository;
import com.switchfully.eurder.item.service.ItemService;
import com.switchfully.eurder.item.service.dtos.CreateItemDto;
import com.switchfully.eurder.item.service.dtos.ItemDto;
import com.switchfully.eurder.item.service.mappers.ItemMapper;
import com.switchfully.eurder.user.domain.Address;
import com.switchfully.eurder.user.domain.Admin;
import com.switchfully.eurder.user.domain.Customer;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.service.CustomerService;
import com.switchfully.eurder.user.service.mappers.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ItemControllerTest {

    private ItemController itemController;
    private ItemRepository itemRepository ;
    private ItemMapper itemMapper;
    private ItemService itemService;
    private CustomerService customerService;
    private UserRepository userRepository;
    private CustomerMapper customerMapper;
    private Item item1;
    private Item item2;
    private Admin admin1;
    private Customer customer1;
    private CreateItemDto createItemDto;
    private static final int SIZE_SETUP = 2;

    @BeforeEach
    void setup(){
        itemRepository = new ItemRepository();
        itemMapper = new ItemMapper();
        customerMapper = new CustomerMapper();
        userRepository = new UserRepository();
        customerService = new CustomerService(userRepository, customerMapper);

        itemService = new ItemService(itemRepository,itemMapper,customerService,userRepository);
        createItemDto = new CreateItemDto("itemName1", "itemDescription1", 10.02, 4);
        item1 = new Item("itemName1", "descrPreUpdate", 1,1);
        itemRepository.saveItem(item1);

        admin1 = new Admin("admin1", LocalDate.of(2023,1,1));
        customer1 = new Customer.CustomerBuilder()
                .withFirstname("fn1")
                .withLastname("ln1")
                .withEmail("email@email.com")
                .withAddress(new Address("street1", "streetNber1", "postalCode1", "city1", "country1"))
                .withPhoneNumber(123456789L)
                .build();
        userRepository.save(admin1);
        userRepository.save(customer1);

        itemController = new ItemController(itemService);
    }


    @Test
    void create_givenAValidCreateItemDtoAndAdminUUID_returnTheExpectedItemDto() {
        //Given
        CreateItemDto createItemDto = new CreateItemDto("name1","descriptionnnnnn",666,666);

        //When
        ItemDto returnedItemDto = itemController.create(createItemDto, admin1.getUuid().toString());

        //Then
        assertEquals(returnedItemDto.getItemName(), createItemDto.getItemName());
        assertEquals(returnedItemDto.getItemInStock(), createItemDto.getItemInStock());
        assertEquals(returnedItemDto.getItemPrice(), createItemDto.getItemPrice());
        assertEquals(returnedItemDto.getItemDescription(), createItemDto.getItemDescription());
    }

    @Test
    void updateItem_givenAValidCreateItemDto_returnUpdatedExistingItem(){
        //Given
        CreateItemDto updateItemDto = new CreateItemDto("itemName1", "descrUpdated", 500,800);

        //When
        ItemDto updatedItemAsDto = itemController.updateItem(updateItemDto, admin1.getUuid().toString());

        //Then
        assertEquals(updatedItemAsDto.getItemUuid(), item1.getItemUuid());
        assertEquals(updatedItemAsDto.getItemDescription(), "descrUpdated");
        assertEquals(updatedItemAsDto.getItemPrice(), 500);
        assertEquals(updatedItemAsDto.getItemInStock(), 800);
    }
}