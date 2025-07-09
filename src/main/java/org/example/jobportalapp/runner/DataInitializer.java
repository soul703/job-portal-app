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
    public void initRoles() {
        for (RoleType type : RoleType.values()) {
            roleRepository.findByRoleName(type)
                    .orElseGet(() -> roleRepository.save(new Role(null, type)));
        }
    }
    @PostConstruct
    public void initUsers() {
        Role adminRole = roleRepository.findByRoleName(RoleType.ADMIN)
                .orElseThrow(() -> new RuntimeException("Role ADMIN chưa được khởi tạo"));
        User adminUser = User.builder()
                .fullName("Main Administrator")
                .email("admin@jobportal.com")
                .password(passwordEncoder.encode("amin123")) // Mã hóa mật khẩu
                .phoneNumber("0901112223")
                .address("1 Admin Way, System City")
                .role(adminRole) // Gán role đã lưu
                .build();
        Role userRole = roleRepository.findByRoleName(RoleType.EMPLOYER)
                .orElseThrow(() -> new RuntimeException("Role USER chưa được khởi tạo"));
        // Tạo 9 users ứng viên (candidates)
        List<User> usersToSave = new java.util.ArrayList<>();
        usersToSave.add(adminUser);

        for (int i = 1; i <= 9; i++) {
            User candidate = User.builder()
                    .fullName("Candidate " + i)
                    .email("candidate" + i + "@jobportal.com")
                    .password(passwordEncoder.encode("user123")) // Mã hóa mật khẩu
                    .phoneNumber("090123456" + i)
                    .address("Address " + i)
                    .role(userRole) // Gán role ứng viên đã lưu
                    .build();
            usersToSave.add(candidate);

        }

        // --- 3. Lưu tất cả user vào database bằng một lệnh duy nhất ---
        userRepository.saveAll(usersToSave);

        System.out.println(userRepository.count() + " users have been created.");
        System.out.println(roleRepository.count() + " roles have been created.");
    }

}

