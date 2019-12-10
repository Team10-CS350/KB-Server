package com.example.demo.dto;

import lombok.Data;

@DTO
@Data
public class UserLoginDTO {
    private String email;
    private String password;
}
