package com.orders.domain.util;

import com.orders.domain.services.DTO.OrderDTO;
import com.orders.gateway.OrderOuterClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toOrder(OrderOuterClass.Order protoOrder);
    OrderOuterClass.Order toProtoOrder(OrderDTO order);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "amount", target = "amount")
    OrderOuterClass.ModifyOrderRequest toModifyOrderRequest(OrderDTO order);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "amount", target = "amount")
    OrderDTO toDTOModifyOrderRequest(OrderOuterClass.ModifyOrderRequest orderRequest);

    @Mapping(source = "id", target = "id")
    OrderOuterClass.DeleteOrderRequest toDeleteOrderRequest(String id);

    @Mapping(source = "id", target = "id")
    OrderDTO toDTODeleteOrderRequest(OrderOuterClass.DeleteOrderRequest orderRequest);


    @Mapping(source = "id", target = "id")
    OrderOuterClass.GetOrderRequest toGetOrderRequest(String id);

    @Mapping(source = "order.id", target = "id")
    @Mapping(source = "order.userId", target = "userId")
    @Mapping(source = "order.amount", target = "amount")
    OrderDTO fromOrderResponse(OrderOuterClass.OrderResponse response);
}
