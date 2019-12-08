package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@DTO
@Data
public class UserDTO {
    @NotNull
    private Long id;

    private String name;
}
