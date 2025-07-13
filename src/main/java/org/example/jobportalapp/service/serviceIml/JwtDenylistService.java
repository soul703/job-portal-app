package org.example.jobportalapp.service.serviceIml;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JwtDenylistService {
    // Sử dụng ConcurrentHashMap cho một cache đơn giản trong bộ nhớ, an toàn với đa luồng
    private final Set<String> denylist = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public void addToDenylist(String token) {
        denylist.add(token);
    }

    public boolean isTokenDenylisted(String token) {
        return denylist.contains(token);
    }
}
