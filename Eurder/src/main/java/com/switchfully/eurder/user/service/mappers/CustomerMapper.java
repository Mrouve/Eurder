package com.switchfully.eurder.user.service.mappers;

import com.switchfully.eurder.user.domain.Customer;
import com.switchfully.eurder.user.service.dtos.CreateCustomerDto;
import com.switchfully.eurder.user.service.dtos.CustomerDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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

    public CustomerDto toDto(Customer customer){
        return new CustomerDto(customer.getUuid(), customer.getFirstname(), customer.getLastname(),
                customer.getEmail(), customer.getAddress(), customer.getPhoneNumber());
    }

    public List<CustomerDto> toDtoList(List<Customer> listOfCustomers){
        return listOfCustomers
                .stream()
                .map(this::toDto)
                .toList();
    }
}

