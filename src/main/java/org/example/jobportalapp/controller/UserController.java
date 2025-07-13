package org.example.jobportalapp.controller;

import jakarta.validation.Valid;
import org.example.jobportalapp.dto.ApiResponse.ApiResponse;
import org.example.jobportalapp.dto.userDTO.UserRequestDTO;
import org.example.jobportalapp.dto.userDTO.UserResponseDTO;
import org.example.jobportalapp.dto.userDTO.UserUpdateDTO;
import org.example.jobportalapp.service.serviceIml.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PreAuthorize("hasRole('ADMIN')")
   @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponseDTO>>> getAllUsers(Pageable pageable) {
        Page<UserResponseDTO> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(ApiResponse.success(users, "Users fetched successfully."));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable String id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user, "Users fetched successfully."));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        ApiResponse<UserResponseDTO> response = ApiResponse.success(createdUser, "User created successfully.");

        // Trả về status 201 Created
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        UserResponseDTO updatedUser = userService.updateUser(id,userUpdateDTO );
        return ResponseEntity.ok(ApiResponse.success(updatedUser, "User updated successfully."));
    }
}
