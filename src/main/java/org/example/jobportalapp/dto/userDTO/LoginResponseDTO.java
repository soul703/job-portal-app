package org.example.jobportalapp.dto.userDTO;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record LoginResponseDTO(
        UUID userId, String email, String username, List<String> roles,
        @NotBlank(message = "Access token is required.")
        String accessToken
//        @NotBlank(message = "Refresh token is required.")
//        String refreshToken;
) {
}
