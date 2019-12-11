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

    @GetMapping(params = { "pageNo", "pageSize" })
    public ResponseDTO<List<EventResponseDTO>> getEvents(@RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        List<Event> list = eventService.getEvents(pageNo, pageSize);
        return ResponseDTO.accepted().convertToList(list, EventResponseDTO.class);
    }

    @GetMapping(params = { "pageNo", "pageSize", "sortBy" })
    public ResponseDTO<List<EventResponseDTO>> getEvents(@RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                                         @RequestParam(defaultValue = "id") String sortBy) {
        List<Event> list = eventService.getEvents(pageNo, pageSize, sortBy);
        return ResponseDTO.accepted().convertToList(list, EventResponseDTO.class);
    }


    @GetMapping("/{id}")
    public ResponseDTO<EventResponseDTO> getEventById(@PathVariable Long id) throws ResourceNotFoundException {
        Optional found = eventService.findEventById(id);

        if (!found.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return ResponseDTO.accepted().convertTo(found.get(), EventResponseDTO.class);
    }

    @PostMapping
    public ResponseDTO<EventResponseDTO> postEvent(@Valid @RequestBody EventPostDTO eventPostDTO) {
        SecurityContext context = SecurityContextHolder.getContext();
        Long userId = Long.parseLong(context.getAuthentication().getPrincipal().toString());

        Event event = new Event();
        User user = userService.findUserById(userId).get();
        event.setUser(user);
        event.getUsers().add(user);

        modelMapper.map(eventPostDTO, event);
        return ResponseDTO.accepted().convertTo(eventService.saveEvent(event), EventResponseDTO.class);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<EventResponseDTO> deleteEvent(@PathVariable Long id) throws PermissionDeniedException {
        Long userId = getCurrentUserId();

        Optional<Event> found = eventService.findEventById(id);

        if (!found.isPresent()) {
            throw new PermissionDeniedException();
        }

        Event event = found.get();

        if (!event.getUser().getId().equals(userId)) {
            throw new PermissionDeniedException();
        }

        eventService.deleteById(id);
        return ResponseDTO.accepted().convertTo(event, EventResponseDTO.class);
    }

    @RequestMapping("/request-join")
    @GetMapping(params = "id")
    ResponseDTO<EventResponseDTO> joinEvent(@RequestParam Long id) throws PermissionDeniedException, AlreadyInEventException {
        Long userId = getCurrentUserId();

        Optional<Event> found = eventService.findEventById(id);

        if (!found.isPresent()) {
            throw new PermissionDeniedException();
        }

        Event event = found.get();

        List<User> eventMembership = event.getUsers().stream().filter(u -> u.getId().equals(userId)).collect(Collectors.toList());

        // If in the event
        if (eventMembership.size() > 0) {
            throw new AlreadyInEventException();
        }

        User newUser = new User();
        newUser.setId(userId);

        eventService.addMember(id, newUser);

        return ResponseDTO.accepted().convertTo(event, EventResponseDTO.class);
    }

    @RequestMapping("/request-leave")
    @GetMapping(params = "id")
    ResponseDTO<EventResponseDTO> leaveEvent(@RequestParam Long id) throws PermissionDeniedException, NotInEventException {
        Long userId = getCurrentUserId();

        Optional<Event> found = eventService.findEventById(id);

        if (!found.isPresent()) {
            throw new PermissionDeniedException();
        }

        Event event = found.get();

        List<User> eventMembership = event.getUsers().stream().filter(u -> u.getId().equals(userId)).collect(Collectors.toList());

        // If not in event
        if (eventMembership.size() == 0) {
            throw new NotInEventException();
        }

        eventService.removeMember(id, eventMembership.get(0));

        return ResponseDTO.accepted().convertTo(event, EventResponseDTO.class);
    }

}
