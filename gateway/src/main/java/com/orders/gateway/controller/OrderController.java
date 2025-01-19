package com.orders.gateway.controller;

import com.orders.gateway.OrderOuterClass;
import com.orders.gateway.model.Order;
import com.orders.gateway.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger LOG = LogManager.getLogger(OrderController.class);

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOuterClass.Order> getOrder(@PathVariable String id) {
        LOG.info("Received request to get order with id: {}", id);
        OrderOuterClass.Order response = orderService.getOrder(id);
        LOG.info("Returning order: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        LOG.info("Received request to create order: {}", order);
        orderService.createOrder("orderExchange", "order.create", order);
        LOG.info("Order creation request sent for order: {}", order);
        return ResponseEntity.accepted().body("Order creation request sent");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable String id, @RequestBody Order order) {
        LOG.info("Received request to update order with id: {}, order: {}", id, order);
        orderService.updateOrder("orderExchange", "order.update", order, id);
        LOG.info("Order update request sent for order with id: {}", id);
        return ResponseEntity.accepted().body("Order update request sent");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable String id) {
        LOG.info("Received request to delete order with id: {}", id);
        orderService.deleteOrder("orderExchange", "order.delete", id);
        LOG.info("Order deletion request sent for order with id: {}", id);
        return ResponseEntity.accepted().body("Order deletion request sent");
    }
}
