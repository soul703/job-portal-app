package org.example.jobportalapp.entity;

import org.example.jobportalapp.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

public class CustomUserDetails implements UserDetails {

    private final User user; // Chứa đối tượng User entity thật sự

    public CustomUserDetails(User user) {
        this.user = Objects.requireNonNull(user," User must not be null");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Lấy thông tin quyền từ đối tượng User thật
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName().name()));
    }

    @Override
    public String getPassword() {
        // Lấy mật khẩu từ đối tượng User thật
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // Lấy email (dùng làm username) từ đối tượng User thật
        return user.getEmail();
    }
    public  UUID getUserId() {
        return user.getId();
    }
    public User getUser() {
        return user;
    }

    // Các phương thức này trả về true theo mặc định
    // Bạn có thể thay đổi logic nếu User entity của bạn có các trường tương ứng (isLocked, isEnabled...)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}