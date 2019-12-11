package com.example.demo.dto.Event;

import com.example.demo.dto.helper.DTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@DTO
@Data
public class EventPostDTO {
    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String description;
}
