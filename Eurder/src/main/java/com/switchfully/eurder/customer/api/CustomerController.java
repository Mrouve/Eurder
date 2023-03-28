package com.switchfully.eurder.customer.api;

import com.switchfully.eurder.customer.service.CustomerService;
import com.switchfully.eurder.customer.service.dtos.CreateCustomerDto;
import com.switchfully.eurder.customer.service.dtos.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping(path= "customer/{uuid}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto getOneCustomerByUuid(@PathVariable UUID uuid){
        return customerService.getOneCustomerByUuid(uuid);
    }


}
