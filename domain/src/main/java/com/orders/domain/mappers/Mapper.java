package com.orders.domain.mappers;

public interface Mapper<M, D> {
    M toModel(D dto);
    D toDTO(M model);
}
