package com.kjj.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshResponse {
    private String accessToken;
    private String refreshToken;

    public static TokenRefreshResponse of(String accessToken, String refreshToken) {
        return new TokenRefreshResponse(
                accessToken,
                refreshToken
        );
    }

    public static TokenRefreshResponse refreshFail(Exception e) {
        return new TokenRefreshResponse(
                "토큰 재발급에 실패했습니다.",
                e.getMessage()
        );
    }
}
