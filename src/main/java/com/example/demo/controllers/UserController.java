package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.dto.DTO;
import com.example.demo.dto.RequestDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.services.UserService;
import lombok.Getter;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = "/users";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.findAllUsers();
//    }

    @GetMapping
    ResponseDTO<List<UserDTO>> getAllUsers() {
        return ResponseDTO.accepted().convertToList(userService.findAllUsers(), UserDTO.class);
    }

//    @GetMapping("/{id}")
//    public User getUser(@PathVariable Long id) {

//    }
    @GetMapping("/{id}")
    ResponseDTO<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseDTO.accepted().convertTo(userService.findUserById(id), UserDTO.class);
    }



    //    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public User saveUser(@RequestBody User user) {
//        return userService.saveUser(user);
//    }
    @PostMapping
    ResponseDTO<UserDTO> saveUser(@RequestDTO(UserDTO.class) @Validated User user) {
        return ResponseDTO.accepted().convertTo(userService.saveUser(user), UserDTO.class);
    }
}
