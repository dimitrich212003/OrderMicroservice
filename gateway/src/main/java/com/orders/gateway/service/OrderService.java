package com.orders.gateway.service;


import com.orders.gateway.OrderOuterClass;
import com.orders.gateway.model.Order;


public interface OrderService {
    OrderOuterClass.Order getOrder(String id);

    void createOrder(String exchange, String routingKey, Order order);

    void updateOrder(String exchange, String routingKey, Order order, String id);

    void deleteOrder(String exchange, String routingKey, String id);
}
