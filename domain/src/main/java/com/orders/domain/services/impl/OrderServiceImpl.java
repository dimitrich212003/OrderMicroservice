package com.orders.domain.services.impl;

import com.orders.domain.mappers.impl.OrderMapper;
import com.orders.domain.models.Order;
import com.orders.domain.repositories.OrdersRepository;
import com.orders.domain.services.DTO.OrderDTO;
import com.orders.domain.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;
    private final OrderMapper orderMapper;


    @Autowired
    public OrderServiceImpl(OrdersRepository ordersRepository, OrderMapper orderMapper) {
        this.ordersRepository = ordersRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toModel(orderDTO);
        order.setOrderDate(LocalDateTime.now());
        Order createdOrder = ordersRepository.save(order);
        return orderMapper.toDTO(createdOrder);
    }

    @Override
    public OrderDTO updateOrder(String id, OrderDTO orderDTO) {
        Order order = ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

        order.setAmount(orderDTO.getAmount());

        Order updateOrder = ordersRepository.save(order);

        return orderMapper.toDTO(updateOrder);
    }

    @Override
    public void deleteOrder(String id) {
        ordersRepository.deleteById(id);
    }

    @Override
    public OrderDTO getOrderById(String id) {
        Order order = ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toDTO(order);
    }
}
