package com.orders.domain.mappers.impl;

import com.orders.domain.mappers.Mapper;
import com.orders.domain.models.Order;
import com.orders.domain.repositories.OrdersRepository;
import com.orders.domain.services.DTO.OrderDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class OrderMapper implements Mapper<Order, OrderDTO> {

    private final ModelMapper modelMapper;
    private final OrdersRepository ordersRepository;

    @Autowired
    public OrderMapper(ModelMapper modelMapper, OrdersRepository ordersRepository) {
        this.modelMapper = modelMapper;
        this.ordersRepository = ordersRepository;
    }

    @Override
    public Order toModel(OrderDTO dto) {
        Order order = modelMapper.map(dto, Order.class);
        if (dto.getOrderDate() != null) {
            order.setOrderDate(LocalDateTime.parse(dto.getOrderDate(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        return order;
    }

    @Override
    public OrderDTO toDTO(Order model) {
        OrderDTO orderDTO = modelMapper.map(model, OrderDTO.class);
        if (model.getOrderDate() != null) {
            orderDTO.setOrderDate(model.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        return orderDTO;
    }
}
