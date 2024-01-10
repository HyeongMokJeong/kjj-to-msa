package com.kjj.noauth.dto.jwt

data class TokenRefreshResponseDto(
    val message: String
) {
    companion object {
        fun refreshSuccess(): TokenRefreshResponseDto {
            return TokenRefreshResponseDto("재발급에 성공했습니다.")
        }

        fun refreshFail(e: Exception): TokenRefreshResponseDto {
            return TokenRefreshResponseDto(e.message!!)
        }
    }
}