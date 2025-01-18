package com.orders.gateway.util;

import com.orders.gateway.OrderOuterClass;
import com.orders.gateway.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderOuterClass.Order protoOrder);
    OrderOuterClass.Order toProtoOrder(Order order);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "amount", target = "amount")
    OrderOuterClass.ModifyOrderRequest toModifyOrderRequest(Order order);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "amount", target = "amount")
    Order toModelModifyOrderRequest(OrderOuterClass.ModifyOrderRequest orderRequest);

    @Mapping(source = "id", target = "id")
    OrderOuterClass.DeleteOrderRequest toDeleteOrderRequest(String id);

    @Mapping(source = "id", target = "id")
    Order toModelDeleteOrderRequest(OrderOuterClass.DeleteOrderRequest orderRequest);


    @Mapping(source = "id", target = "id")
    OrderOuterClass.GetOrderRequest toGetOrderRequest(String id);

    @Mapping(source = "order.id", target = "id")
    @Mapping(source = "order.userId", target = "userId")
    @Mapping(source = "order.amount", target = "amount")
    Order fromOrderResponse(OrderOuterClass.OrderResponse response);
}