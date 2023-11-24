package com.kjj.noauth.dto.user;

import com.kjj.noauth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinDto {
    private String username;
    private String password;

    public static JoinDto from(User user) {
        return new JoinDto(
                user.getUsername(),
                user.getPassword()
        );
    }

    public void setEncodePassword(BCryptPasswordEncoder bCryptPasswordEncoder) {
        password = bCryptPasswordEncoder.encode(password);
    }

    public boolean checkForm() {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) return false;
        return true;
    }
}
