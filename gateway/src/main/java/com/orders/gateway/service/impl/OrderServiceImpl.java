package com.orders.gateway.service.impl;


import com.orders.gateway.OrderOuterClass;
import com.orders.gateway.model.Order;
import com.orders.gateway.service.OrderService;
import com.orders.gateway.service.RabbitMQSender;
import com.orders.gateway.service.grpc.GrpcOrderClient;
import com.orders.gateway.service.redis.CacheService;
import com.orders.gateway.util.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {

    private final GrpcOrderClient grpcClient;
    private final RabbitMQSender rabbitMQSender;
    private final OrderMapper orderMapper;
    private final CacheService cacheService;

    @Autowired
    public OrderServiceImpl(GrpcOrderClient grpcClient, RabbitMQSender rabbitMQSender, OrderMapper orderMapper, CacheService cacheService) {
        this.grpcClient = grpcClient;
        this.rabbitMQSender = rabbitMQSender;
        this.orderMapper = orderMapper;
        this.cacheService = cacheService;
    }

    @Override
    public OrderOuterClass.Order getOrder(String id) {
        String cacheKey = "order:" + id;

        OrderOuterClass.Order cachedOrder = (OrderOuterClass.Order) cacheService.get(cacheKey);
        if (cachedOrder != null) {
            return cachedOrder;
        }

        // Если нету в кэше
        OrderOuterClass.Order order = grpcClient.getOrder(id);

        //Сохраняем результат на 10 минут
        cacheService.set(cacheKey, order, 10, TimeUnit.MINUTES);

        return order;
    }

    @Override
    public void createOrder(String exchange, String routingKey, Order order) {
        OrderOuterClass.ModifyOrderRequest orderRequest = orderMapper.toModifyOrderRequest(order);
        byte[] message = orderRequest.toByteArray();
        rabbitMQSender.sendMessage(exchange, routingKey, message);
    }

    @Override
    public void updateOrder(String exchange, String routingKey, Order order, String id) {
        OrderOuterClass.ModifyOrderRequest orderRequest = orderMapper.toModifyOrderRequest(order);
        byte[] message = orderRequest.toByteArray();
        rabbitMQSender.sendUpdateMessage(exchange, routingKey, message, id);
    }

    @Override
    public void deleteOrder(String exchange, String routingKey, String id) {
        OrderOuterClass.DeleteOrderRequest orderRequest = OrderOuterClass.DeleteOrderRequest.newBuilder().setId(id).build();
        byte[] message = orderRequest.toByteArray();
        rabbitMQSender.sendMessage(exchange, routingKey, message);
    }
}
