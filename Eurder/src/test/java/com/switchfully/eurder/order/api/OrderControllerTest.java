package com.switchfully.eurder.order.api;

import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.domain.ItemRepository;
import com.switchfully.eurder.item.service.ItemService;
import com.switchfully.eurder.item.service.dtos.CreateItemDto;
import com.switchfully.eurder.item.service.mappers.ItemMapper;
import com.switchfully.eurder.order.domain.ItemGroups;
import com.switchfully.eurder.order.domain.OrderRepository;
import com.switchfully.eurder.order.service.OrderService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderControllerTest {
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
    private OrderController orderController;

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
        orderController = new OrderController(orderService);

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
    void create_givenAValidListOfUserInputsForOrder_thenIsReturnedTheExpectedOrderDto() {
        //Given
        List<UserInputOrderDto> userInput= new ArrayList<>();
        UserInputOrderDto ui1 = new UserInputOrderDto("name1", 1);
        UserInputOrderDto ui2 = new UserInputOrderDto("name2", 1);
        userInput.add(ui1);
        userInput.add(ui2);

        List<ItemGroups> itemGroupsListMockup = new ArrayList<>();
        ItemGroups ig1 = new ItemGroups(item1.getItemUuid(),1,item1.getItemPrice(),LocalDate.now().plusDays(1));
        ItemGroups ig2 = new ItemGroups(item2.getItemUuid(),1,item2.getItemPrice(),LocalDate.now().plusDays(1));
        itemGroupsListMockup.add(ig1);
        itemGroupsListMockup.add(ig2);

        //When
        OrderDto orderDtoReturnedToController = orderController.create(userInput, customer1.getUuid().toString());

        //Then
        assertEquals(orderDtoReturnedToController.getOrderDate(), LocalDate.now());
        assertEquals(orderDtoReturnedToController.getTotalPrice(), (item1.getItemPrice() + item2.getItemPrice()));
        assertEquals(orderDtoReturnedToController.getCustomerId(), customer1.getUuid());
        //assertEquals(orderDtoReturnedToController.getItemGroups(),itemGroupsListMockup);
        assertTrue(orderDtoReturnedToController.getItemGroups().get(0).getItemId() == item1.getItemUuid());
        assertTrue(orderDtoReturnedToController.getItemGroups().get(1).getItemId() == item2.getItemUuid());
        assertTrue(orderDtoReturnedToController.getItemGroups().get(0).getQuantity() == ui1.getQuantity());
        assertTrue(orderDtoReturnedToController.getItemGroups().get(0).getPriceAtTimeOfOrder() == item1.getItemPrice());
    }
}