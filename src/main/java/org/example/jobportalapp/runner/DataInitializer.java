package org.example.jobportalapp.runner;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.jobportalapp.entity.Role;
import org.example.jobportalapp.entity.User;
import org.example.jobportalapp.myEnum.RoleType;
import org.example.jobportalapp.repository.RoleRepository;
import org.example.jobportalapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;
    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void initData() {
        // --- 1. TẠO VÀ LƯU CÁC ROLES TRƯỚC TIÊN ---
        // Phương thức save() sẽ trả về đối tượng Role đã được lưu (có ID)
        System.out.println("Creating and saving roles...");
        Role roleAdmin = roleRepository.save(Role.builder().roleName(RoleType.ADMIN).build());
        Role roleEmpl = roleRepository.save(Role.builder().roleName(RoleType.EMPLOYER).build());
        Role roleJobSeek = roleRepository.save(Role.builder().roleName(RoleType.JOB_SEEKER).build());
        System.out.println("Roles created successfully.");

        // --- 2. BÂY GIỜ MỚI
        // TẠO USERS ---
        // Bây giờ, các biến roleAdmin, roleEmpl đã là các đối tượng Entity
        // thật sự, được quản lý bởi Hibernate và có ID.

        System.out.println("Creating users...");
        // Tạo một user admin
        User adminUser = User.builder()
                .fullName("Main Administrator")
                .email("admin@jobportal.com")
                .password(passwordEncoder.encode("admin123"))
                .phoneNumber("0901112223")
                .address("1 Admin Way, System City")
                .role(roleAdmin) // <-- Bây giờ gán role đã lưu một cách an toàn
                .build();

        // Tạo các users ứng viên (candidates)
        List<User> usersToSave = new java.util.ArrayList<>();
        usersToSave.add(adminUser);

        for (int i = 1; i <= 9; i++) {
            User candidate = User.builder()
                    .fullName("Candidate " + i)
                    .email("candidate" + i + "@example.com")
                    .password(passwordEncoder.encode("password" + i))
                    .phoneNumber("090000000" + i)
                    .address(i + " Example Street, User City")
                    .role(roleEmpl) // <-- Gán role candidate một cách an toàn
                    .build();
            usersToSave.add(candidate);
        }

        // --- 3. LƯU TẤT CẢ USER VÀO DATABASE ---
        userRepository.saveAll(usersToSave);

        System.out.println(userRepository.count() + " users have been created.");
    }

}

