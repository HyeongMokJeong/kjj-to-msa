package com.kjj.noauth.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String username;
    private String password;

    public boolean checkForm() {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) return false;
        return true;
    }
}
