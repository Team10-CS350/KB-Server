package com.example.demo.services.Event;

import com.example.demo.domain.Event;
import com.example.demo.domain.User;
import com.example.demo.repositories.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Optional<Event> findEventById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getEvents(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Event> pagedResult = eventRepository.findAll(paging);
        return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>();
    }

    @Override
    public List<Event> getEvents(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Event> pagedResult = eventRepository.findAll(paging);
        return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>();
    }

    @Override
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Event addMember(Long eventId, User user) {
        Event event = eventRepository.findById(eventId).get();
        event.getUsers().add(user);

        return event;
    }

    @Transactional
    @Override
    public Event removeMember(Long eventId, User user) {
        Event event = eventRepository.findById(eventId).get();

        if (event.getUsers().size() == 1) {
            eventRepository.delete(event);
            return event;
        }

        event.getUsers().remove(user);
        return event;
    }
}
