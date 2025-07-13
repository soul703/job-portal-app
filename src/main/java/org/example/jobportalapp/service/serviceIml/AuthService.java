package org.example.jobportalapp.service.serviceIml;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.jobportalapp.config.JwtTokenProvider;
import org.example.jobportalapp.dto.userDTO.LoginRequestDTO;
import org.example.jobportalapp.dto.userDTO.LoginResponseDTO;
import org.example.jobportalapp.entity.CustomUserDetails;
import org.example.jobportalapp.entity.RefreshToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final JwtDenylistService denylistService;
//    private final RefreshTokenService refreshTokenService;



    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );

        Object principal = authentication.getPrincipal();
        CustomUserDetails customUserDetails;

        if (principal instanceof CustomUserDetails) {
            customUserDetails = (CustomUserDetails) principal;
            if (!customUserDetails.isEnabled()) {
                throw new RuntimeException("User account is disabled");
            }
        } else {
            throw new IllegalStateException("Authentication principal is not an instance of CustomUserDetails");
        }

        // Tạo token
        String accessToken = jwtTokenProvider.generateToken(customUserDetails);

        // Lấy các thông tin cần thiết từ customUserDetails
        UUID userId = customUserDetails.getUserId();
        String email = customUserDetails.getUsername(); // Vì username của bạn là email
        String username = customUserDetails.getUser().getFullName(); // Lấy username thật từ User entity

        // Chuyển đổi Collection<GrantedAuthority> thành List<String>
        List<String> roles = customUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Tạo và trả về đối tượng DTO hoàn chỉnh
        return new LoginResponseDTO( userId, email, username, roles,accessToken);
    }
    public void logout(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            denylistService.addToDenylist(token);
        }
    }
}