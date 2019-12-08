package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.services.UserService;
import com.example.demo.exceptions.types.UserNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    ResponseDTO<UserDTO> getUserById(@PathVariable Long id) throws UserNotFoundException {
        Optional found = userService.findUserById(id);

        if (!found.isPresent()) {
            throw new UserNotFoundException();

        }

    //    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public User saveUser(@RequestBody User user) {
//        return userService.saveUser(user);
//    }

    // TODO: Should make a UserAcceptDTO class
//    @PostMapping
//    ResponseDTO<UserDTO> saveUser(@RequestDTO(UserDTO.class) @Validated User user) {
//        User newUser = userService.saveUser(user);
//        return ResponseDTO.accepted().convertTo(newUser, UserDTO.class);
//    }

    @PostMapping
    ResponseDTO<UserDTO> saveUser(@RequestBody User user) {
        return ResponseDTO.accepted().convertTo(userService.saveUser(user), UserDTO.class);
    }
}
