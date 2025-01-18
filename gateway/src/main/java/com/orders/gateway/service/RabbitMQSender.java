package com.orders.gateway.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Отправляем сжатое сообщение в байтах
    public void sendMessage(String exchange, String routingKey, byte[] message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    public void sendUpdateMessage(String exchange, String routingKey, byte[] message, String id) {
        // Создаем свойства сообщения и добавляем id в заголовки
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("id", id);

        // Создаем сообщение с телом и свойствами
        Message amqpMessage = MessageBuilder.withBody(message)
                .andProperties(messageProperties)
                .build();

        // Отправляем сообщение
        rabbitTemplate.convertAndSend(exchange, routingKey, amqpMessage);
    }
}
