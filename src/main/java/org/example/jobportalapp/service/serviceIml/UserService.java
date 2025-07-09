package org.example.jobportalapp.service.serviceIml;

import jakarta.persistence.EntityManager;
import org.example.jobportalapp.dto.ApiResponse.ApiResponse;
import org.example.jobportalapp.dto.userDTO.UserRequestDTO;
import org.example.jobportalapp.dto.userDTO.UserResponseDTO;
import org.example.jobportalapp.dto.userDTO.UserUpdateDTO;
import org.example.jobportalapp.entity.Role;
import org.example.jobportalapp.entity.User;
import org.example.jobportalapp.exception.ResourceNotFoundException;
import org.example.jobportalapp.mapper.UserMapper;
import org.example.jobportalapp.repository.RoleRepository;
import org.example.jobportalapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements org.example.jobportalapp.service.UserService {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found in the database.");
        }
        return users.map(userMapper::toDTO);
    }

    @Override
    public UserResponseDTO getUserById(String userId) {
        UUID uuid = UUID.fromString(userId);
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        // Set the role for the user
        Role role = roleRepository.findById(userRequestDTO.roleId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + userRequestDTO.roleId()));
        user.setRole(role);
        // encrypt the password before saving
        String password = userRequestDTO.password();
        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        // Save the user to the database
        User saveUser = userRepository.save(user);
        saveUser.getRole().getRoleName();
        return userMapper.toDTO(saveUser);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(String userId, UserUpdateDTO userUpdateDTO) {
        UUID uuid ;
        try {
            uuid = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid UUID format for userId: " + userId, e);
        }
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        if(userUpdateDTO.fullName()!= null && !userUpdateDTO.fullName().isBlank()) {
            user.setFullName(userUpdateDTO.fullName());
        }
        if(userUpdateDTO.email() != null && !userUpdateDTO.email().isBlank()) {
            if (userRepository.existsByEmail(userUpdateDTO.email())) {
                throw new IllegalArgumentException("Email already exists: " + userUpdateDTO.email());
            }
            user.setEmail(userUpdateDTO.email());
        }
        if (userUpdateDTO.phoneNumber() != null) {
            user.setPhoneNumber(userUpdateDTO.phoneNumber());
        }
        if (userUpdateDTO.address() != null) {
            user.setAddress(userUpdateDTO.address());
        }
        return userMapper.toDTO(user);
    }

    @Override
    public void deleteUser(String userId) {
        UUID uuid = UUID.fromString(userId);
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        userRepository.delete(user);
    }
}


