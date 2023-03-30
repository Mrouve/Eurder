package com.switchfully.eurder.order.api;

import com.switchfully.eurder.order.domain.Order;
import com.switchfully.eurder.order.service.OrderService;
import com.switchfully.eurder.order.service.dtos.UserInputOrderDto;
import com.switchfully.eurder.user.service.CustomerService;
import com.switchfully.eurder.user.service.dtos.CreateCustomerDto;
import com.switchfully.eurder.user.service.dtos.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    //POST==============================================================================================================
    @PostMapping(path="", consumes = "application/json", produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody List<UserInputOrderDto> userInputOrderDto){
        return orderService.saveOrder(userInputOrderDto);
    }
}
