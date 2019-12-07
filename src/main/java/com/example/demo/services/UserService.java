package com.example.demo.services;

import com.example.demo.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    User findUserById(Long id);
    List<User> findAllUsers();
    User saveUser(User user);
}

