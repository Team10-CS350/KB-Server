package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.dto.helper.ResponseDTO;
import com.example.demo.dto.User.UserDTO;
import com.example.demo.dto.User.UserRegistrationDTO;
import com.example.demo.exceptions.types.UserAlreadyRegisteredException;
import com.example.demo.services.User.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseDTO<UserDTO> registerUserAccount(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) throws UserAlreadyRegisteredException {
        if (userService.findByEmail(userRegistrationDTO.getEmail()).isPresent()) {
            throw new UserAlreadyRegisteredException();
        }

        return ResponseDTO.accepted().convertTo(userService.registerUser(userRegistrationDTO), UserDTO.class);
    }
}
