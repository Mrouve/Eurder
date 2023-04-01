package com.switchfully.eurder.order.domain;

import com.switchfully.eurder.item.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepository {
    private final ConcurrentHashMap<UUID, Order> ordersByUuid;
    public OrderRepository(){
        this.ordersByUuid = new ConcurrentHashMap<>();
    }

    public Order saveOrder(Order order){
        ordersByUuid.put(order.getOrderUuid(), order);
        return order;
    }

    public ConcurrentHashMap<UUID, Order> getOrdersByUuid() {
        return ordersByUuid;
    }
}
