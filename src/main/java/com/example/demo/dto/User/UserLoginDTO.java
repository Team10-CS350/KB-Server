package com.example.demo.dto.User;

import com.example.demo.dto.helper.DTO;
import lombok.Data;

@DTO
@Data
public class UserLoginDTO {
    private Long id;
    private String password;
}
