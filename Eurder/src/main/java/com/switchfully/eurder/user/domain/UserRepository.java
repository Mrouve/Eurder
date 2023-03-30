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

    public User save(User user){
        usersByUUID.put(user.getUuid(), user);
        return user;
    }

    public Collection<User> getAllUsers(){
        return new ArrayList<>(usersByUUID
                .values());
    }

    public User getOneUserByUuid(UUID uuid){
//        if(usersByUUID.get(uuid) != null){
//            return usersByUUID.get(uuid);
//        }
//        return null;
        return usersByUUID.get(uuid);
    }

    public Role getUserRole(UUID uuid){
        return getOneUserByUuid(uuid).getRole();
    }

    public User getOneCustomerByUuid(UUID uuid){
        User desiredUser = getOneUserByUuid(uuid);
        if(desiredUser instanceof Customer){
            return usersByUUID.get(uuid);
        }
        return null;
    }

//    public User getOneCustomerByUuid(UUID uuid){
//        if(usersByUUID.get(uuid) instanceof Customer){
//            return usersByUUID.get(uuid);
//        }
//        return null;
//    }
}
