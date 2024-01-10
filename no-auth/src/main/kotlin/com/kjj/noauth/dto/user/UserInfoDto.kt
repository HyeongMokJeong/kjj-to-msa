package com.kjj.noauth.dto.user

data class UserInfoDto(
    val id: Long,
    val username: String
) {
    companion object {
        fun from(userDto: UserDto): UserInfoDto {
            return UserInfoDto(
                userDto.id!!,
                userDto.username
            )
        }

        fun loginFail(message: String): UserInfoDto {
            return UserInfoDto(
                0,
                message
            )
        }
    }
}