package com.orders.domain.config;

import com.orders.domain.services.OrderService;
import com.orders.domain.services.grpc.GrpcOrderServiceImpl;
import com.orders.domain.util.OrderMapper;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServerConfig {

    private final OrderMapper orderMapper;
    private final OrderService orderService;

    @Autowired
    public GrpcServerConfig(OrderMapper orderMapper, OrderService orderService) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
    }

    @Bean
    public GrpcServerConfigurer grpcServerConfigurer() {
        return serverBuilder -> {
            serverBuilder.addService(new GrpcOrderServiceImpl(orderService, orderMapper));
        };
    }
}
