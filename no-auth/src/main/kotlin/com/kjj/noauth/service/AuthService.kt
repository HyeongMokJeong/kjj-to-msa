package com.kjj.noauth.service

import com.kjj.noauth.dto.jwt.TokenRefreshResponseDto
import com.kjj.noauth.dto.user.JoinOrLoginDto
import com.kjj.noauth.dto.user.UserDto
import com.kjj.noauth.dto.user.UserInfoDto
import com.kjj.noauth.exception.CantFindByUsernameException
import com.kjj.noauth.exception.JwtRefreshException
import com.kjj.noauth.tool.HttpTool
import com.kjj.noauth.tool.jwt.JwtTemplate
import com.kjj.noauth.tool.jwt.JwtTool
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userService: UserService,
    private val jwtTool: JwtTool,
    private val jwtTemplate: JwtTemplate,
    private val keycloakService: KeycloakService,
    private val httpTool: HttpTool
) {
    fun refreshJwt(response: HttpServletResponse, refreshToken: String): TokenRefreshResponseDto {
        if (jwtTool.isTokenExpired(refreshToken)) throw JwtRefreshException("Refresh 토큰이 만료되었습니다.")
        else if (jwtTool.getTypeFromRefreshToken(refreshToken) != jwtTemplate.refreshType) throw JwtRefreshException("토큰 형식이 잘못되었습니다.")

        val username: String = jwtTool.getUsernameFromToken(refreshToken)
        val userDto = userService.loadUserByUsername(username)

        httpTool.setTokenToHttpServletResponse(response, userDto)
        return TokenRefreshResponseDto.refreshSuccess()
    }

    fun defaultLogin(response: HttpServletResponse, dto: JoinOrLoginDto): UserInfoDto {
        val userDto: UserDto = userService.loadUserByUsername(dto.username)

        httpTool.setTokenToHttpServletResponse(response, userDto)
        return UserInfoDto.from(userDto)
    }

    fun keycloakLogin(response: HttpServletResponse, keycloakToken: String): UserInfoDto {
        val keycloakUserInfo = keycloakService.getUserInfoFromKeycloak((keycloakToken))
        val sub = keycloakService.generateUsernameFromSub(keycloakUserInfo)
        val roles = keycloakService.checkKeycloakUserRoles(keycloakUserInfo)

        val userDto: UserDto = try {
            userService.loadUserByUsername(sub)
        } catch (e: CantFindByUsernameException) {
            userService.joinKeycloak(sub, roles)
        }

        httpTool.setTokenToHttpServletResponse(response, userDto)
        return UserInfoDto.from(userDto)
    }
}