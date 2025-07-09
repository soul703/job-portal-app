package org.example.jobportalapp.dto.userDTO;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String fullName,
        String email,
        String phoneNumber,
        String address,
        String roleName
) {}