package com.kjj.noauth.dto.user

data class JoinOrLoginDto(
    var username: String,
    val password: String
) {
    companion object {
        fun from(userDto: UserDto): JoinOrLoginDto {
            return JoinOrLoginDto(
                userDto.username,
                userDto.password
            )
        }
    }

    fun checkForm(): Boolean {
        return (username.isNotEmpty() && password.isNotEmpty())
    }
}