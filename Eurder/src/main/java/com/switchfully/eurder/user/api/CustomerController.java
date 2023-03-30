package com.switchfully.eurder.user.api;

import com.switchfully.eurder.user.service.CustomerService;
import com.switchfully.eurder.user.service.dtos.CreateCustomerDto;
import com.switchfully.eurder.user.service.dtos.CustomerDto;
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
    @GetMapping(path="", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getAllCustomers(@RequestHeader String userId){
        return customerService.getAllCustomers(userId);
    }

    @GetMapping(path= "customer/{uuid}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto getOneCustomerByUuid(@PathVariable String uuid, @RequestHeader String userId){
        return customerService.getOneCustomerByUuid(uuid, userId);
    }


}
