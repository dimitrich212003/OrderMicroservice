package com.orders.domain.services;

import com.orders.domain.services.DTO.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(String id, UserDTO userDTO);

    void deleteUser(String id);

    UserDTO getUserById(String id);

    List<UserDTO> getAllUsers();
}
