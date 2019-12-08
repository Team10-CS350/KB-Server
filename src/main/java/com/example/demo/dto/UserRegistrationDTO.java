package com.example.demo.dto;

import com.example.demo.util.FieldMatch;
import lombok.Data;

import javax.validation.constraints.*;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
})

@DTO
@Data
public class UserRegistrationDTO {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String confirmPassword;

    @Email
    @NotEmpty
    private String email;
}