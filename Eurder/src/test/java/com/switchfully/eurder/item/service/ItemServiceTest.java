package com.switchfully.eurder.item.service;

import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.domain.ItemRepository;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatRuntimeException;
import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {

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
        item1 = new Item("itemName1", "descr", 40, 40);
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
    }

    @Test
    void saveItem_givenAValidCreateItemDto_thenShouldBeReturnedAValidItemDto_CaseUserIsAdmin() {
        //Given
        CreateItemDto newCreateItemDto = new CreateItemDto("test", "desc", 1,1);

        //When
        ItemDto ItemToSaveDto = itemService.saveItem(newCreateItemDto, admin1.getUuid().toString());

        //Then
        assertNotNull(ItemToSaveDto.getItemUuid());
        assertEquals(ItemToSaveDto.getItemName(), newCreateItemDto.getItemName());
        assertEquals(ItemToSaveDto.getItemDescription(), newCreateItemDto.getItemDescription());
        assertEquals(ItemToSaveDto.getItemPrice(), newCreateItemDto.getItemPrice());
        assertEquals(ItemToSaveDto.getItemInStock(), newCreateItemDto.getItemInStock());
    }

    @Test
    void saveItem_givenAValidCreateItemDto_thenShouldThrowUnauthorizedEndPointException_CaseUserIsNotAdmin() {
        //Given
        UUID notAdminUuid = customer1.getUuid();

        //Then
        assertThatRuntimeException()
                .isThrownBy(() -> itemService.saveItem(createItemDto, customer1.getUuid().toString()))
                .withMessage("Unauthorized End Point !");
    }

    @Test
    void saveItem_givenAValidCreateItemDto_thenShouldThrowUnauthorizedEndPointException_CaseUserIdIsNotAValidUserId() {
        //Given
        String notAValidUuidFormat = "fdsfdsfds";

        //Then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> itemService.saveItem(createItemDto, notAValidUuidFormat))
                .withMessage("Invalid UUID string: " + notAValidUuidFormat);
    }

    @Test
    void saveItem_givenAValidCreateItemDto_thenShouldThrowInvalidUuidException_CaseUserIdIsNotAnExistingUserId() {
        //Given
        UUID randomUuid = UUID.randomUUID();

        //Then
        assertThatRuntimeException()
                .isThrownBy(() -> itemService.saveItem(createItemDto, randomUuid.toString()))
                .withMessage("This Unique Id does not exists");
    }

    @Test
    void checkIfNameExists_givenANewItemName_thenShouldReturnTheExpectedStringMessage(){
        //Given
        CreateItemDto itemDtoWithNewName = new CreateItemDto("newNameRandom","descr", 100,100);

        //When
        String stringReturned = itemService.checkIfNameExists(itemDtoWithNewName, "create");

        //Then
        assertEquals(stringReturned, "No Exception, lets continue");


    }

    @Test
    void checkIfNameExists_givenAnItemNameThatAlreadyExists_thenShouldThrowException(){
        //Given item1 and createItemDto as defined in setup (same name)
        //Then
        assertThatRuntimeException()
                .isThrownBy(() -> itemService.checkIfNameExists(createItemDto,"create"))
                .withMessage("An item with the exact same name already exists");
    }

    @Test
    void updateItem_givenACreateItemDto_thenReturnTheUpdatedCorrespondingItem(){
        //Given
        CreateItemDto updateItemDto = new CreateItemDto("itemName1", "descrUpdated", 500,800);

        //When
        ItemDto existingItemToUpdate = itemService.updateItem(updateItemDto, admin1.getUuid().toString());

        //Then
        assertEquals(existingItemToUpdate.getItemUuid(), item1.getItemUuid());
        assertEquals(existingItemToUpdate.getItemDescription(), updateItemDto.getItemDescription());
        assertEquals(existingItemToUpdate.getItemPrice(), updateItemDto.getItemPrice());
        assertEquals(existingItemToUpdate.getItemInStock(), updateItemDto.getItemInStock());
    }
}