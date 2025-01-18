package com.orders.domain.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "orders")
public class Order extends BaseEntity {

    @Field("order_date")
    private LocalDateTime orderDate;

    @Field("amount")
    private Double amount;

    @Field("user_id")
    private String userId;

    public Order() {
        super();  // Конструктор BaseEntity будет вызван автоматически, String будет сгенерирован.
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
