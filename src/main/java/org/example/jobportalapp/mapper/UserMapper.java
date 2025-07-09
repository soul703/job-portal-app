package org.example.jobportalapp.mapper;

import org.example.jobportalapp.dto.userDTO.UserRequestDTO;
import org.example.jobportalapp.dto.userDTO.UserResponseDTO;
import org.example.jobportalapp.entity.User;

public class UserMapper {
    public static User toEntity(UserRequestDTO dto) {
        return User.builder()
                .fullName(dto.fullName())
                .email(dto.email())
                .password(dto.password()) // nên mã hóa ở service
                .phoneNumber(dto.phoneNumber())
                .address(dto.address())
                .build();
    }

    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getRole().getName()
        );
    }
}