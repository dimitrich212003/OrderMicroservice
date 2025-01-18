package com.orders.domain.services.impl;

import com.orders.domain.models.User;
import com.orders.domain.repositories.UsersRepository;
import com.orders.domain.services.DTO.UserDTO;
import com.orders.domain.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User createdUser = usersRepository.save(user);
        return modelMapper.map(createdUser, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(String id, UserDTO userDTO) {
        User user = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());

        User updateUser = usersRepository.save(user);

        return modelMapper.map(updateUser, UserDTO.class);
    }

    @Override
    public void deleteUser(String id) {
        usersRepository.deleteById(id);
    }

    @Override
    public UserDTO getUserById(String id) {
        User user = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = usersRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
}
