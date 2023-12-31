package com.kjj.noauth.service;

import com.kjj.noauth.client.UserClient;
import com.kjj.noauth.dto.user.JoinDto;
import com.kjj.noauth.dto.user.UserDto;
import com.kjj.noauth.dto.user.WithdrawDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserClient userClient;

    public UserDto loadUserByUsername(String username) {
        return userClient.findByUsername(username);
    }

    public boolean join(JoinDto dto) {
        return userClient.join(dto);
    }

    public UserDto joinKeycloak(String sub, String roles) {
        return userClient.joinKeycloak(UserDto.ofKeycloak(sub, roles));
    }

    public boolean checkLoginId(String username) {
        return userClient.existsByUsername(username);
    }

    public boolean withdraw(String username, WithdrawDto dto) {
        if (userClient.checkPassword(username, dto.getPassword())) {
            return userClient.withdraw(username);
        }
        else return false;
    }

    public boolean withdraw(String username) {
        return userClient.withdraw(username);
    }
}
