package com.example.demo.dto.User;

import com.example.demo.dto.helper.DTO;
import lombok.Data;

import javax.validation.constraints.NotNull;

@DTO
@Data
// For debugging
public class UserDTO {
    @NotNull
    private Long id;

    private String name;
}
