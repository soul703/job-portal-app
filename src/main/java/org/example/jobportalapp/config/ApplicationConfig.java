package org.example.jobportalapp.config;

import io.jsonwebtoken.security.Keys;
import org.example.jobportalapp.entity.CustomUserDetails;
import org.example.jobportalapp.entity.User;
import org.example.jobportalapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Value("${app.jwt.secret}")
    private String jwtSecretString;

    public ApplicationConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Bean cung cấp UserDetailsService (giống như CustomUserDetailsService trước đây)
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // 1. Tải User entity từ DB như bình thường
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

            // 2. Tạo một đối tượng CustomUserDetails từ User entity đó và trả về
            return new CustomUserDetails(user);
        };
    }

    // Bean cung cấp PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean cung cấp SecretKey
    @Bean
    public SecretKey jwtSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretString.getBytes(StandardCharsets.UTF_8));
    }
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    // 2. TẠO AUTHENTICATION MANAGER TỪ CẤU HÌNH
    //    Hàm này không thay đổi, nhưng bây giờ nó sẽ sử dụng provider bạn đã định nghĩa ở trên.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
