package com.orders.domain.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public class User extends BaseEntity {

    @Field("name")
    private String name;

    @Field("email")
    private String email;

    public User() {
        super();  // Конструктор BaseEntity будет вызван автоматически, String будет сгенерирован.
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

