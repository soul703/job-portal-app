package org.example.jobportalapp.dto.userDTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "Full name is required")
        String fullName,

        @Email(message = "Email must be valid")
        @NotBlank(message = "Email is required")
        String email,

        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        String phoneNumber,
        String address,

        @NotNull(message = "Role ID is required")
        Long roleId
) {}