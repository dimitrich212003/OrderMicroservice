package com.orders.domain.services.grpc;

import com.orders.domain.services.DTO.OrderDTO;
import com.orders.domain.services.OrderService;
import com.orders.domain.util.OrderMapper;
import com.orders.gateway.OrderOuterClass;
import com.orders.gateway.OrderServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrpcOrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    private final OrderService orderService; // Ваш бизнес-сервис для работы с заказами
    private final OrderMapper orderMapper;   // MapStruct или иной маппер для преобразования объектов

    @Autowired
    public GrpcOrderServiceImpl(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @Override
    public void getOrder(OrderOuterClass.GetOrderRequest request, StreamObserver<OrderOuterClass.OrderResponse> responseObserver) {

        String orderId = request.getId();

        OrderDTO resultOrderDTO = orderService.getOrderById(orderId);

        // Преобразование OrderDTO в Protobuf Order
        OrderOuterClass.Order resultOrder = orderMapper.toProtoOrder(resultOrderDTO);

        OrderOuterClass.OrderResponse response = OrderOuterClass.OrderResponse.newBuilder()
                .setOrder(resultOrder)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}


