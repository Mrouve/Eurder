package com.switchfully.eurder.customer.api;

import com.switchfully.eurder.customer.service.CustomerService;
import com.switchfully.eurder.customer.service.dtos.CreateCustomerDto;
import com.switchfully.eurder.customer.service.dtos.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {

    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    //POST==============================================================================================================
    @PostMapping(path="", consumes = "application/json", produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto create(@RequestBody CreateCustomerDto createCustomerDto){
        return customerService.save(createCustomerDto);
    }


    //GET===============================================================================================================

}
