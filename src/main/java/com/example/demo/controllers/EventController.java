package com.example.demo.controllers;

import com.example.demo.domain.Event;
import com.example.demo.domain.User;
import com.example.demo.dto.Event.EventPostDTO;
import com.example.demo.dto.Event.EventResponseDTO;
import com.example.demo.dto.helper.ResponseDTO;
import com.example.demo.exceptions.types.AlreadyInEventException;
import com.example.demo.exceptions.types.NotInEventException;
import com.example.demo.exceptions.types.PermissionDeniedException;
import com.example.demo.exceptions.types.ResourceNotFoundException;
import com.example.demo.services.Event.EventService;
import com.example.demo.services.User.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

}
