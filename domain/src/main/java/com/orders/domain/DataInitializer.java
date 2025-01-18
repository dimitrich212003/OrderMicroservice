package com.orders.domain;

import com.orders.domain.services.DTO.OrderDTO;
import com.orders.domain.services.DTO.UserDTO;
import com.orders.domain.services.OrderService;
import com.orders.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public DataInitializer(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) {
        //Пользователи
        UserDTO user = new UserDTO();
        user.setName("Дима");
        user.setEmail("ytrewq212003@mail.ru");
        UserDTO createdUser = userService.createUser(user);

        UserDTO user1 = new UserDTO();
        user1.setName("Миша");
        user1.setEmail("qwerty192004@mail.ru");
        UserDTO createdUser1 = userService.createUser(user1);

        //Заказы
        OrderDTO order = new OrderDTO();
        order.setAmount(1200.0);
        order.setUserId(createdUser.getId());
        OrderDTO createdOrder = orderService.createOrder(order);

        OrderDTO order1 = new OrderDTO();
        order1.setAmount(1488.0);
        order1.setUserId(createdUser1.getId());
        OrderDTO createdOrder1 = orderService.createOrder(order1);

        OrderDTO order2 = new OrderDTO();
        order2.setAmount(1488.0);
        order2.setUserId(createdUser.getId());
        OrderDTO createdOrder2 = orderService.createOrder(order2);

    }
}
