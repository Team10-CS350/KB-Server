package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.services.UserService;
import com.example.demo.exceptions.types.UserNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping
    ResponseDTO<List<UserDTO>> getAllUsers() {
        return ResponseDTO.accepted().convertToList(userService.findAllUsers(), UserDTO.class);
    }

    @GetMapping("/{id}")
    ResponseDTO<UserDTO> getUserById(@PathVariable Long id) throws UserNotFoundException {
        Optional found = userService.findUserById(id);

        if (!found.isPresent()) {
            throw new UserNotFoundException();

        }

        return ResponseDTO.accepted().convertTo(found.get(), UserDTO.class);
    }

    // TODO: Should make a UserAcceptDTO class
    // TODO: or not

    @PostMapping
    ResponseDTO<UserDTO> saveUser(@Valid @RequestBody User user) {
        return ResponseDTO.accepted().convertTo(userService.saveUser(user), UserDTO.class);
    }

}
