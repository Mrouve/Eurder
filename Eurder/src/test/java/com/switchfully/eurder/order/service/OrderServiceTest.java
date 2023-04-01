package com.switchfully.eurder.order.service;

import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.domain.ItemRepository;
import com.switchfully.eurder.item.service.ItemService;
import com.switchfully.eurder.item.service.dtos.CreateItemDto;
import com.switchfully.eurder.item.service.dtos.ItemDto;
import com.switchfully.eurder.item.service.mappers.ItemMapper;
import com.switchfully.eurder.order.domain.ItemGroups;
import com.switchfully.eurder.order.domain.OrderRepository;
import com.switchfully.eurder.order.service.dtos.OrderDto;
import com.switchfully.eurder.order.service.dtos.UserInputOrderDto;
import com.switchfully.eurder.order.service.mappers.OrderMapper;
import com.switchfully.eurder.user.domain.Address;
import com.switchfully.eurder.user.domain.Admin;
import com.switchfully.eurder.user.domain.Customer;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.service.CustomerService;
import com.switchfully.eurder.user.service.mappers.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceTest {
    private ItemRepository itemRepository ;
    private ItemMapper itemMapper;
    private ItemService itemService;
    private CustomerService customerService;
    private UserRepository userRepository;
    private CustomerMapper customerMapper;
    private Item item1;
    private Item item2;
    private Item item3;
    private Admin admin1;
    private Customer customer1;
    private CreateItemDto createItemDto;

    private OrderRepository orderRepository;
    private OrderService orderService;
    private OrderMapper orderMapper;

    @BeforeEach
    void setup(){
        itemRepository = new ItemRepository();
        itemMapper = new ItemMapper();
        customerMapper = new CustomerMapper();
        userRepository = new UserRepository();
        customerService = new CustomerService(userRepository, customerMapper);

        orderRepository = new OrderRepository();
        orderMapper = new OrderMapper();
        orderService = new OrderService(orderRepository, orderMapper, itemRepository, itemMapper, customerService);

        itemService = new ItemService(itemRepository,itemMapper,customerService,userRepository);
        createItemDto = new CreateItemDto("itemName1", "itemDescription1", 10.02, 4);

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

        item1 = new Item("name1", "descr1", 10.00, 1);
        item2 = new Item("name2", "descr2", 20.00, 2);
        item3 = new Item("name3", "descr3", 100.00, 200);
        itemRepository.saveItem(item1);
        itemRepository.saveItem(item2);
        itemRepository.saveItem(item3);
    }

    @Test
    void saveOrder() {
        //Given
        List<UserInputOrderDto> userInput= new ArrayList<>();
        UserInputOrderDto ui1 = new UserInputOrderDto("name1", 1);
        UserInputOrderDto ui2 = new UserInputOrderDto("name2", 1);
        userInput.add(ui1);
        userInput.add(ui2);

        //When
        OrderDto orderDto = orderService.saveOrder(userInput, customer1.getUuid().toString());

        //Then
        assertEquals(orderDto.getOrderDate(), LocalDate.now());
        assertEquals(orderDto.getCustomerId(), customer1.getUuid());
        assertEquals(orderDto.getTotalPrice(), 30.0);
        assertEquals(orderDto.getItemGroups().get(0).getItemId(),item1.getItemUuid() );
    }
//
//    @Test
//    void fetchDesiredItems() {
//    }
//
    @Test
    void createItemGroups() {
        //Given
        List<UserInputOrderDto> userInput= new ArrayList<>();
        UserInputOrderDto ui1 = new UserInputOrderDto("name1", 1);
        UserInputOrderDto ui2 = new UserInputOrderDto("name2", 1);
        userInput.add(ui1);
        userInput.add(ui2);

        List<ItemDto> listItemsDto = new ArrayList<>();
        ItemDto i1 = itemMapper.toDto(item1);
        ItemDto i2 = itemMapper.toDto(item2);
        listItemsDto.add(i1);
        listItemsDto.add(i2);

        List<ItemGroups> itemGroupsListMockup = new ArrayList<>();
        ItemGroups ig1 = new ItemGroups(item1.getItemUuid(),1,item1.getItemPrice(),LocalDate.now().plusDays(1));
        ItemGroups ig2 = new ItemGroups(item2.getItemUuid(),1,item2.getItemPrice(),LocalDate.now().plusDays(1));
        itemGroupsListMockup.add(ig1);
        itemGroupsListMockup.add(ig2);

        //When
        List<ItemGroups> itemGroupsList = orderService.createItemGroups(userInput, listItemsDto);

        //Then
        assertTrue(itemGroupsList.get(1).getItemId() == itemGroupsListMockup.get(1).getItemId());
        assertTrue(itemGroupsList.get(1).getQuantity() == itemGroupsListMockup.get(1).getQuantity());
        assertTrue(itemGroupsList.get(1).getPriceAtTimeOfOrder() == itemGroupsListMockup.get(1).getPriceAtTimeOfOrder());
        assertEquals(itemGroupsList.get(1).getShipmentDate(), itemGroupsListMockup.get(1).getShipmentDate());
    }
}