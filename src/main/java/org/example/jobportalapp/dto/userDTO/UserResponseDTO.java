package org.example.jobportalapp.dto.userDTO;

public record UserResponseDTO(
        String id,
        String fullName,
        String email,
        String phoneNumber,
        String address,
        String roleName
) {}