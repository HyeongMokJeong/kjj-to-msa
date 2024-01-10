package com.kjj.noauth.tool

import com.kjj.noauth.dto.user.UserDto
import com.kjj.noauth.tool.jwt.JwtTemplate
import com.kjj.noauth.tool.jwt.JwtTool
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component

@Component
class HttpTool(
    val jwtTemplate: JwtTemplate,
    val jwtTool: JwtTool
) {
    fun setTokenToHttpServletResponse(response: HttpServletResponse, userDto: UserDto) {
        val accessToken: String = jwtTool.createToken(userDto)
        val refreshToken: String = jwtTool.createRefreshToken(userDto)

        response.setHeader(jwtTemplate.headerString, jwtTemplate.tokenPrefix + accessToken)
        response.setHeader(jwtTemplate.refreshHeaderString, jwtTemplate.tokenPrefix + refreshToken)
    }

    fun <T> getRequestEntityTypeJson(bodyObject: T): HttpEntity<T> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        return HttpEntity(bodyObject, headers)
    }
}