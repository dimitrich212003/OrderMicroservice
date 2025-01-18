package com.orders.gateway.service.grpc;

import com.orders.gateway.OrderOuterClass;
import com.orders.gateway.OrderServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.orders.gateway.OrderOuterClass.OrderResponse;
import com.orders.gateway.OrderOuterClass.Order;


@Component
public class GrpcOrderClient {

    private final OrderServiceGrpc.OrderServiceBlockingStub stub;

    @Autowired
    public GrpcOrderClient(OrderServiceGrpc.OrderServiceBlockingStub stub) {
        this.stub = stub;
    }

    public Order getOrder(String id) {
        OrderOuterClass.GetOrderRequest request = OrderOuterClass.GetOrderRequest.newBuilder()
                .setId(id)
                .build();

        // Отправляем запрос и получаем ответ
        OrderResponse response = stub.getOrder(request);

        // Возвращаем объект Order из ответа
        return response.getOrder();
    }
}
