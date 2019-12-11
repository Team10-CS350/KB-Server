package com.example.demo.dto.User;

import com.example.demo.dto.helper.DTO;
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
    @Size(min = 8, max = 200)
    private String password;

    @NotNull
    @NotEmpty
    private String confirmPassword;

    @NotEmpty
    @NotEmpty
    @Email
    @Pattern(regexp=".*@kaist\\.ac\\.kr$", message="Not a valid KAIST email address")
    private String email;
}