package com.switchfully.eurder.user.service;

import com.switchfully.eurder.user.domain.*;
import com.switchfully.eurder.user.exceptions.InvalidUuidException;
import com.switchfully.eurder.user.exceptions.UnauthorizedEndPointException;
import com.switchfully.eurder.user.service.dtos.CreateCustomerDto;
import com.switchfully.eurder.user.service.dtos.CustomerDto;
import com.switchfully.eurder.user.service.mappers.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final UserRepository userRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(UserRepository userRepository, CustomerMapper customerMapper) {
        this.userRepository = userRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDto save(CreateCustomerDto createCustomerDto){
        Customer newCustomer = customerMapper.fromDto(createCustomerDto);
        User customerToCreateInRepository = userRepository.save(newCustomer);
        return customerMapper.toDto((Customer)customerToCreateInRepository);
    }

    public List<CustomerDto> getAllCustomers(String userId){
        checkIfValidUuidFormat(userId);
        checkIfAdmin(userId);
        List<Customer> listOfCustomers = userRepository
                .getAllUsers()
                .stream()
                .filter(c -> c instanceof Customer)
                .map(cc -> (Customer) cc)
                .distinct()
                .sorted(Comparator.comparing(Customer::getLastname))
                .toList();

        //listOfCustomers.sort(Customer::getLastname);
        return customerMapper.toDtoList(listOfCustomers);
    }

    public CustomerDto getOneCustomerByUuid(String uuid, String userId){
        checkIfValidUuidFormat(userId);
        checkIfUuidExists(userId);
        checkIfAdmin(userId);
        checkIfUuidExists(uuid);
        Customer customerToGet = (Customer) userRepository.getOneCustomerByUuid(UUID.fromString(uuid));
        return customerMapper.toDto(customerToGet);
    }

    public void checkIfAdmin(String userId){
        if(userRepository.getUserRole(UUID.fromString(userId)) != Role.ADMIN){
            throw new UnauthorizedEndPointException();
        }
    }

    public void checkIfCustomer(String userId){
        if(userRepository.getUserRole(UUID.fromString(userId)) != Role.MEMBER){
            throw new UnauthorizedEndPointException();
        }
    }

    public void checkIfUuidExists(String userId){
        if(userRepository.getOneUserByUuid(UUID.fromString(userId))==null){
            throw new InvalidUuidException();
        }
    }

    public void checkIfValidUuidFormat(String userId){
        try{UUID.fromString(userId);
        }catch(IllegalArgumentException iae){
            iae.getMessage();
        }
    }


    //TEMP - to delete after test phase
    public List<Admin> getDefaultAdmin(){
        List<Admin> defaultAdmin = userRepository
                .getAllUsers()
                .stream()
                .filter(c -> c instanceof Admin)
                .map(cc -> (Admin) cc)
                .distinct()
                .toList();
        return defaultAdmin;
    }
}
