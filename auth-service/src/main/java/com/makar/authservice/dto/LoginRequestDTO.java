package com.makar.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @NotBlank(message = "Email field cannot be empty")
        @Email(message = "Enter a valid email")
        String userEmail,

        @NotBlank(message = "Password field cannot be empty")
        @Size(min = 8 , message = "Password must be of minimum 8 characters")
        String userPassword
) {
}
