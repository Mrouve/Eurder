package com.switchfully.eurder.customer.domain;

import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CustomerRepository {

    private final ConcurrentHashMap<UUID, Customer> customersByUUID;

    public CustomerRepository() {
        this.customersByUUID = new ConcurrentHashMap<>();
    }

    public Customer save(Customer customer){
        Customer savedCustomer = customersByUUID.put(customer.getUuid(), customer);
        return customer;
    }
}
