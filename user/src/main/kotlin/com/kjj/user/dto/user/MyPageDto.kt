package com.kjj.user.dto.user

import com.kjj.user.entity.UserMypage

data class MyPageDto(
    val userId: Long?,
    val point: Int
) {
    companion object {
        fun from(userMyPage: UserMypage): MyPageDto {
            return MyPageDto(
                userMyPage.id,
                userMyPage.point
            )
        }
    }
}