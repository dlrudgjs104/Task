package com.example.demo.domain;

import jakarta.validation.constraints.*;
import lombok.Value;

@Value
public class StudentRegisterRequest {
    @NotNull
    String id;
    @NotNull
    String password;

    @NotBlank
    String name;

    @Email
    String email;

    @Max(100)
    @Min(0)
    int score;

    @NotBlank
    @Size(min = 1, max = 200)
    String evaluation;

}
