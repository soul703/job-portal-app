package org.example.jobportalapp.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.jobportalapp.service.serviceIml.JwtDenylistService; // Giả sử đường dẫn đúng
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final JwtDenylistService denylistService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService,
                                   JwtDenylistService denylistService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.denylistService = denylistService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        // --- BƯỚC 1: KIỂM TRA SƠ BỘ TOKEN ---
        // Nếu không có header, hoặc không bắt đầu bằng "Bearer ", hoặc token đã bị logout (trong denylist)
        // thì bỏ qua và chuyển cho các filter tiếp theo.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        if (denylistService.isTokenDenylisted(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        // --- BƯỚC 2: XÁC THỰC TOKEN (CHỈ KHI TOKEN HỢP LỆ) ---
        final String userEmail = jwtTokenProvider.getUsernameFromJWT(jwt);

        // Chỉ xử lý nếu có userEmail và chưa có ai được xác thực trong context
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Kiểm tra token có hợp lệ với userDetails không
            if (jwtTokenProvider.validateToken(jwt, userDetails)) {
                // Tạo đối tượng xác thực
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                // Đặt đối tượng xác thực vào SecurityContext.
                // Từ đây Spring Security sẽ biết người dùng này đã được xác thực.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Luôn gọi filterChain.doFilter ở cuối để request được tiếp tục xử lý
        filterChain.doFilter(request, response);
    }
}