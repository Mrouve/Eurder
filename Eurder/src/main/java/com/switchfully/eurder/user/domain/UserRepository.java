package com.switchfully.eurder.user.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private final ConcurrentHashMap<UUID, User> usersByUUID;

    public UserRepository() {
        this.usersByUUID = new ConcurrentHashMap<>();
    }

    public Customer save(Customer customer){
        usersByUUID.put(customer.getUuid(), customer);
        return customer;
    }

    public Collection<User> getAllUsers(){
        return new ArrayList<>(usersByUUID
                .values());
    }


    public User getOneCustomerByUuid(UUID uuid){
//        return usersByUUID.get(uuid);
        if(usersByUUID.get(uuid) instanceof Customer){
            return usersByUUID.get(uuid);
        }
        return null;
    }
}
