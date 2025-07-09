package org.example.jobportalapp.dto.userDTO;

import jakarta.validation.constraints.Email;

public record UserUpdateDTO(
        String fullName,
        @Email(message = "Email must be valid")
        String email,
        String phoneNumber,
        String address
) {}