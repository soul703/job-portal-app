package org.example.jobportalapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.jobportalapp.dto.ApiResponse.ApiResponse;
import org.example.jobportalapp.dto.userDTO.LoginRequestDTO;
import org.example.jobportalapp.dto.userDTO.LoginResponseDTO;
import org.example.jobportalapp.service.serviceIml.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO loginResponseDTO = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success(loginResponseDTO, "Login successful."));
    }
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.ok(ApiResponse.success(null, "Logout successful."));
    }


    }
