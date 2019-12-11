package com.example.demo.dto.Event;

import com.example.demo.domain.User;
import com.example.demo.dto.helper.DTO;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@DTO
@Data
public class EventResponseDTO {
    private Long id;
    private String title;
    private String description;

    @JsonProperty("author")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private User user;

    private Date createdAt;

    @JsonProperty("members")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private Set<User> users = new HashSet<>();
}
