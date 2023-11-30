package com.kjj.noauth.service;

import com.kjj.noauth.dto.jwt.TokenRefreshResponseDto;
import com.kjj.noauth.dto.keycloak.KeycloakUserInfoDto;
import com.kjj.noauth.dto.user.LoginDto;
import com.kjj.noauth.dto.user.UserInfoDto;
import com.kjj.noauth.dto.user.UserDto;
import com.kjj.noauth.exception.CantFindByUsernameException;
import com.kjj.noauth.exception.JwtRefreshException;
import com.kjj.noauth.util.HttpTools;
import com.kjj.noauth.util.jwt.JwtTemplate;
import com.kjj.noauth.util.jwt.JwtTools;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTools jwtTools;
    private final JwtTemplate jwtTemplate;
    private final KeycloakService keycloakService;
    private final HttpTools httpTools;

    public TokenRefreshResponseDto refreshJwt(HttpServletResponse response, String refreshToken) throws JwtRefreshException {
        if (jwtTools.isTokenExpired(refreshToken)) throw new JwtRefreshException("Refresh 토큰이 만료되었습니다.");
        else if (!jwtTools.getTypeFromRefreshToken(refreshToken).equals(jwtTemplate.getRefreshType())) throw new JwtRefreshException("토큰 형식이 잘못되었습니다.");

        String username = jwtTools.getUsernameFromRefreshToken(refreshToken);
        UserDto userDto = userService.loadUserByUsername(username);

        httpTools.setTokenToHttpServletResponse(response, userDto);

        return TokenRefreshResponseDto.refreshSuccess();
    }

    public UserInfoDto defaultLogin(HttpServletResponse response, LoginDto dto) {
        UserDto userDto = userService.loadUserByUsername(dto.getUsername());
        if (userDto == null) throw new CantFindByUsernameException("""
                해당 username을 가진 유저를 찾을 수 없습니다.
                username : """ + dto.getUsername());

        httpTools.setTokenToHttpServletResponse(response, userDto);

        return UserInfoDto.from(userDto);
    }

    public UserInfoDto keycloakLogin(HttpServletResponse response, String keycloakToken) {
        KeycloakUserInfoDto keycloakUserInfo = keycloakService.getUserInfoFromKeycloak(keycloakToken);
        String sub = keycloakService.generateUsernameFromSub(keycloakUserInfo);
        String roles = keycloakService.checkKeycloakUserRoles(keycloakUserInfo);

        UserDto userDto = userService.loadUserByUsername(sub);
        if (userDto == null) userDto = userService.joinKeycloak(sub, roles);

        httpTools.setTokenToHttpServletResponse(response, userDto);

        return UserInfoDto.from(userDto);
    }
}
