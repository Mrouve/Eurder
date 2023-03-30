package com.switchfully.eurder.user.service;

import com.switchfully.eurder.user.domain.Customer;
import com.switchfully.eurder.user.domain.CustomerRepository;
import com.switchfully.eurder.user.exceptions.InvalidUuidException;
import com.switchfully.eurder.user.service.dtos.CreateCustomerDto;
import com.switchfully.eurder.user.service.dtos.CustomerDto;
import com.switchfully.eurder.user.service.mappers.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDto save(CreateCustomerDto createCustomerDto){
        Customer newCustomer = customerMapper.fromDto(createCustomerDto);
        Customer customerToCreateInRepository = customerRepository.save(newCustomer);
        return customerMapper.toDto(customerToCreateInRepository);
    }

    public List<CustomerDto> getAllCustomers(){
        checkIfAdmin();
        List<Customer> listOfCustomers = customerRepository
                .getAllCustomers()
                .stream()
                .toList();
        return customerMapper.toDtoList(listOfCustomers);
    }

    public CustomerDto getOneCustomerByUuid(UUID uuid){
        checkIfAdmin();
        Customer customerToGet = customerRepository.getOneCustomerByUuid(uuid);
        if(customerToGet == null){
            throw new InvalidUuidException();
        }
        return customerMapper.toDto(customerToGet);
    }

    public void checkIfAdmin(){
        //TO DO
        // throw new UnauthorizedEndPointException();
    }
}
