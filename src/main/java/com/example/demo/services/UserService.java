package com.example.demo.services;

import com.example.demo.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findUserById(Long id);
    List<User> findAllUsers();

    User saveUser(User user);
}

