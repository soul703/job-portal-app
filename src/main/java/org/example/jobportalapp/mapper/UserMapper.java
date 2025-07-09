package org.example.jobportalapp.mapper;

import org.example.jobportalapp.dto.userDTO.UserRequestDTO;
import org.example.jobportalapp.dto.userDTO.UserResponseDTO;
import org.example.jobportalapp.entity.Role;
import org.example.jobportalapp.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public  User toEntity(UserRequestDTO dto) {
        return User.builder()
                .fullName(dto.fullName())
                .email(dto.email())
                .password(dto.password()) // nên mã hóa ở service
                .phoneNumber(dto.phoneNumber())
                .address(dto.address())
//                .role(Role.builder()
//                        .id(dto.roleId())
//                        .build())
                .build();
    }

    public  UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getRole().getRoleName().name()

        );
    }
}