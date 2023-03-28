package com.switchfully.eurder.customer.service.mappers;

import com.switchfully.eurder.customer.domain.Customer;
import com.switchfully.eurder.customer.service.dtos.CreateCustomerDto;

public class CustomerMapper {
    public Customer fromDto(CreateCustomerDto customerDto) {
        Customer customer = new Customer.CustomerBuilder()
                .withFirstname(customerDto.getFirstname())
                .withLastname(customerDto.getLastname())
                .withEmail(customerDto.getEmail())
                .withAddress(customerDto.getAddress())
                .withPhoneNumber(customerDto.getPhoneNumber())
                .build();
        return customer;
    }
}

