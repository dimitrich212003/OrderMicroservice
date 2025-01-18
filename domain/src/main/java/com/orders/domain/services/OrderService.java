package com.orders.domain.services;

import com.orders.domain.services.DTO.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO updateOrder(String id, OrderDTO orderDTO);

    void deleteOrder(String id);

    OrderDTO getOrderById(String id);
}
