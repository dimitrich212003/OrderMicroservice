package com.orders.gateway.controller;

import com.orders.gateway.OrderOuterClass;
import com.orders.gateway.model.Order;
import com.orders.gateway.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOuterClass.Order> getOrder(@PathVariable String id) {
        OrderOuterClass.Order response = orderService.getOrder(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        orderService.createOrder("orderExchange", "order.create", order);
        return ResponseEntity.accepted().body("Order creation request sent");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable String id, @RequestBody Order order) {
        orderService.updateOrder("orderExchange", "order.update", order, id);
        return ResponseEntity.accepted().body("Order update request sent");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder("orderExchange", "order.delete", id);
        return ResponseEntity.accepted().body("Order deletion request sent");
    }
}
