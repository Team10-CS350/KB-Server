package com.example.demo.dto.User;


import com.example.demo.dto.helper.DTO;
import lombok.Data;

import javax.validation.constraints.NotNull;

@DTO
@Data
public class UserResponseDTO {
    @NotNull
    private Long id;

    private String name;

    private boolean isBuddy;
}
