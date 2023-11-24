package com.kjj.auth.service;

import com.kjj.auth.entity.User;
import com.kjj.auth.util.jwt.JwtTools;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTools jwtTools;

    private boolean checkUser(User user) {
        if (user.getUsername() == null) return false;
        if (user.getPassword() == null) return false;
        if (user.getRoles() == null) return false;
        return true;
    }

    public boolean checkJwt(String token) {
        String username = jwtTools.getUsernameFromToken(token);
        return checkUser(userService.loadUserByUsername(username));
    }
}
