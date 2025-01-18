package com.orders.domain.MQContoller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.orders.domain.services.DTO.OrderDTO;
import com.orders.domain.services.OrderService;
import com.orders.domain.util.OrderMapper;
import com.orders.gateway.OrderOuterClass;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQListener {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Autowired
    public RabbitMQListener(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "orderQueue", durable = "true"),
            exchange = @Exchange(value = "orderExchange", type = "topic"),
            key = {"order.create", "order.update", "order.delete"}))
    public void handleOrderMessage(Message message) {
        try {

            byte[] messageBody = message.getBody();

            String routingKey = message.getMessageProperties().getReceivedRoutingKey();
            String id = (String) message.getMessageProperties().getHeaders().get("id");

            // Вызов нужного метода на основе ключа маршрутизации
            switch (routingKey) {
                case "order.create":
                    OrderOuterClass.ModifyOrderRequest protoCreateMessage = OrderOuterClass.ModifyOrderRequest.parseFrom(messageBody);
                    OrderDTO orderCreateDTO = orderMapper.toDTOModifyOrderRequest(protoCreateMessage);
                    handleCreate(orderCreateDTO);
                    break;
                case "order.update":
                    OrderOuterClass.ModifyOrderRequest protoUpdateMessage = OrderOuterClass.ModifyOrderRequest.parseFrom(messageBody);
                    OrderDTO orderUpdateDTO = orderMapper.toDTOModifyOrderRequest(protoUpdateMessage);
                    handleUpdate(id, orderUpdateDTO);
                    break;
                case "order.delete":
                    OrderOuterClass.DeleteOrderRequest protoDeleteMessage = OrderOuterClass.DeleteOrderRequest.parseFrom(messageBody);
                    OrderDTO orderDeleteDTO = orderMapper.toDTODeleteOrderRequest(protoDeleteMessage);
                    handleDelete(orderDeleteDTO.getId());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported routing key: " + routingKey);
            }
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException("Failed to parse protobuf message ", e);
        }
    }

    private void handleCreate(OrderDTO orderDTO) {
        orderService.createOrder(orderDTO);
    }

    private void handleUpdate(String id, OrderDTO orderDTO) {
        orderService.updateOrder(id, orderDTO);
    }

    private void handleDelete(String id) {
        orderService.deleteOrder(id);
    }
}
