package com.kjj.user.dto.user;

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

    public void setEncodePassword(BCryptPasswordEncoder bCryptPasswordEncoder) {
        password = bCryptPasswordEncoder.encode(password);
    }
}
