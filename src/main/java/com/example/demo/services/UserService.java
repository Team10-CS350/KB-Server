package com.example.demo.services;

import com.example.demo.domain.User;
import com.example.demo.dto.UserRegistrationDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findUserById(Long id);
    List<User> findAllUsers();
    User saveUser(User user);
    Optional<User> findByEmail(String email);
//    User registerUser(UserRegistrationDTO userRegistrationDTO);

    String login(String email, String password);
}

