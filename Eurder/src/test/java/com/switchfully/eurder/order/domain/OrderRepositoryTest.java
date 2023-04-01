package com.switchfully.eurder.order.domain;

import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.domain.ItemRepository;
import com.switchfully.eurder.order.service.OrderService;
import com.switchfully.eurder.user.domain.*;
import com.switchfully.eurder.user.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderRepositoryTest {

    private OrderRepository orderRepository = new OrderRepository();
    private UserRepository userRepository = new UserRepository();
    private ItemRepository itemRepository = new ItemRepository();
    private Order order1;
    private Admin admin;
    private Customer customer;
    private Item item1;
    private Item item2;
    List<ItemGroups> allItemGroups;
    ItemGroups ig1 ;
    ItemGroups ig2 ;

    @Autowired
    private OrderService orderService ;

    @BeforeEach
    void setup(){
        userRepository = new UserRepository();
        customer = new Customer("fn1","ln1", "aaa@aaa.com", new Address("rer","rer","gdfg","fdgd", "fsdf"),123456789L );
        admin = new Admin("admin1", LocalDate.of(1987,2,2));
        userRepository.save(customer);
        userRepository.save(admin);

        itemRepository = new ItemRepository();
        item1 = new Item("itemName1","descr1",60.00,10);
        item2 = new Item("itemName2","descr2",40.00,2);
        itemRepository.saveItem(item1);
        itemRepository.saveItem(item2);

        ig1 = new ItemGroups(item1.getItemUuid(),1, item1.getItemPrice(),LocalDate.now().plusDays(1));
        ig2 = new ItemGroups(item2.getItemUuid(),5, item2.getItemPrice(),LocalDate.now().plusDays(7));
        allItemGroups = new ArrayList<>();
        allItemGroups.add(ig1);
        allItemGroups.add(ig2);


        orderRepository = new OrderRepository();
        order1 = new Order(LocalDate.now(), 100.00, customer.getUuid(),allItemGroups);
        orderRepository.saveOrder(order1);

    }

    @Test
    void saveOrder_givenAnOrderToSaveAndUserIsCustomer_thenSavedOrderIsContainedInRepo() {
        //Given
        Order orderToSave = order1;

        //When
        Order savedOrder = orderRepository.saveOrder(orderToSave);

        //Then
        assertTrue(orderRepository.getOrdersByUuid().contains(savedOrder));
    }
}