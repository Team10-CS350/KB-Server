package com.example.demo.services.Event;

import com.example.demo.domain.Event;
import com.example.demo.domain.User;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Optional<Event> findEventById(Long id);
    Event saveEvent(Event event);
    List<Event> getEvents(Integer pageNo, Integer pageSize);
    List<Event> getEvents(Integer pageNo, Integer pageSize, String sortBy);
    void deleteById(Long id);
    Event addMember(Long eventId, User user);
    Event removeMember(Long eventId, User user);
}
