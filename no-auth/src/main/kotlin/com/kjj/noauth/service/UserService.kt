package com.kjj.noauth.service

import com.kjj.noauth.client.UserClient
import com.kjj.noauth.dto.user.JoinOrLoginDto
import com.kjj.noauth.dto.user.UserDto
import com.kjj.noauth.dto.user.WithdrawDto
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userClient: UserClient
) {
    fun loadUserByUsername(username: String): UserDto {
        return userClient.findByUsername(username)
    }

    fun join(dto: JoinOrLoginDto): Boolean {
        return userClient.join(dto)
    }

    fun joinKeycloak(sub: String, roles: String): UserDto {
        return userClient.joinKeycloak(UserDto.ofKeycloak(sub, roles))
    }

    fun checkLoginId(username: String): Boolean {
        return userClient.existsByUsername(username)
    }

    fun withdraw(username: String, dto: WithdrawDto): Boolean {
        return if (userClient.checkPassword(username, dto.password)) {
            userClient.withdraw(username)
        } else false
    }

    fun withdraw(username: String): Boolean {
        return userClient.withdraw(username)
    }
}