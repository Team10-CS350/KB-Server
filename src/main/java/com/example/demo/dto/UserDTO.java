package com.example.demo.dto;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.ui.ModelMap;

import javax.validation.constraints.NotNull;

@DTO
@Data
public class UserDTO {
    @NotNull
    private Long id;

    private String name;
}
