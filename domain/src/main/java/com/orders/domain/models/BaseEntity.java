package com.orders.domain.models;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.UUID;


@Document // Аннотация для сущности MongoDB (если не указано, коллекция берется по имени класса)
public abstract class BaseEntity {

    @Id
    protected String id = UUID.randomUUID().toString(); // Генерируем UUID как строку

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
