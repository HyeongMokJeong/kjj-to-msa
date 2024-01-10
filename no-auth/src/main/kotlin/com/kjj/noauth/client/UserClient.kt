package com.kjj.noauth.client

import com.kjj.noauth.dto.user.JoinOrLoginDto
import com.kjj.noauth.dto.user.UserDto
import com.kjj.noauth.exception.CantFindByUsernameException
import com.kjj.noauth.tool.HttpTool
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URI

@Service
class UserClient(
    private val restTemplate: RestTemplate,
    private val httpTool: HttpTool,
    @Value("\${my.client.user.host}") private val userServerHost: String
) {
    private fun getRequestURI(path: String): URI {
        return URI(userServerHost + path)
    }

    private fun getRequestURI(path: String, params: Map<String, String>): URI {
        val stringBuilder = StringBuilder("$userServerHost$path?")
        for ((key, value) in params) {
            stringBuilder.append(java.lang.String.join("=", key, value))
            stringBuilder.append("&")
        }
        return URI(stringBuilder.toString())
    }

    fun findByUsername(username: String): UserDto {
        val uri = getRequestURI(
            "/v1/client/user/info/all",
            mapOf(Pair("username", username))
        )
        return restTemplate.getForObject(uri, UserDto::class.java)
            ?: throw CantFindByUsernameException("""
                해당 username을 가진 유저를 찾을 수 없습니다.
                username = $username
                """
            )
    }

    fun existsByUsername(username: String): Boolean {
        val uri = getRequestURI("/v1/client/user/check", mapOf(Pair("username", username)))
        return restTemplate.getForObject(uri, Boolean::class.java)!!
    }

    fun join(dto: JoinOrLoginDto): Boolean {
        val uri = getRequestURI("/v1/client/user/join")
        return restTemplate.postForObject(uri, httpTool.getRequestEntityTypeJson(dto), Boolean::class.java)!!
    }

    fun joinKeycloak(dto: UserDto): UserDto {
        val uri = getRequestURI("/v1/client/user/join/keycloak")
        return restTemplate.postForObject(uri, httpTool.getRequestEntityTypeJson(dto), UserDto::class.java)!!
    }

    fun withdraw(username: String): Boolean {
        val uri = getRequestURI("/v1/client/user/withdraw", mapOf(Pair("username", username)))
        return restTemplate.getForObject(uri, Boolean::class.java)!!
    }

    fun checkPassword(username: String, password: String): Boolean {
        val uri = getRequestURI(
            "/v1/client/user/check/password",
            mapOf(
                Pair("username", username),
                Pair("password", password))
        )
        return restTemplate.getForObject(uri, Boolean::class.java)!!
    }
}