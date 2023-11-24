package com.kjj.noauth.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshResponseDto {
    private String message;

    public static TokenRefreshResponseDto refreshSuccess() {
        return new TokenRefreshResponseDto(
                "재발급에 성공했습니다."
        );
    }
    public static TokenRefreshResponseDto refreshFail(Exception e) {
        return new TokenRefreshResponseDto(
                e.getMessage()
        );
    }
}
