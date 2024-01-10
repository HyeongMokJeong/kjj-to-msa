package com.kjj.noauth.service

import com.kjj.noauth.dto.keycloak.KeycloakUserInfoDto
import com.kjj.noauth.dto.user.JoinOrLoginDto
import com.kjj.noauth.exception.CantFindByUsernameException
import com.kjj.noauth.exception.JoinKeycloakException
import com.kjj.noauth.exception.KeycloakLoginException
import com.kjj.noauth.exception.KeycloakWithdrawException
import com.kjj.noauth.external.KeycloakProperties
import com.kjj.noauth.tool.jwt.JwtTemplate
import org.keycloak.admin.client.CreatedResponseUtil
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Service
class KeycloakService(
    private val keycloak:Keycloak,
    private val userService: UserService,
    private val properties: KeycloakProperties,
    private val restTemplate: RestTemplate,
    private val jwtTemplate: JwtTemplate,
    @Value("\${keycloak.realm}") private val realm: String
) {

    private fun createRequestEntity(token: String): HttpEntity<MultiValueMap<String, String>> {
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Authorization", jwtTemplate.tokenPrefix + token)
        return HttpEntity(httpHeaders)
    }

    fun getUserInfoFromKeycloak(keycloakToken: String): KeycloakUserInfoDto {
        val url = "${properties.host}/auth/realms/${properties.realmName}/protocol/openid-connect/userinfo"
        val response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            createRequestEntity(keycloakToken),
            KeycloakUserInfoDto::class.java
        )
        val body = response.body ?: throw KeycloakLoginException("keycloak에서 유저 정보를 조회할 수 없습니다.")
        keycloak.tokenManager().invalidate(keycloakToken)
        return body
    }

    fun generateUsernameFromSub(dto: KeycloakUserInfoDto): String {
        return dto.sub + "_keycloak"
    }

    fun checkKeycloakUserRoles(dto: KeycloakUserInfoDto): String {
        val roleUser = "ROLE_USER"
        val roleManager = "ROLE_MANAGER"
        val roles = listOf(*dto.roles)
        return if (roles.contains(roleManager)) roleManager
        else if (roles.contains(roleUser)) roleUser
        else throw KeycloakLoginException("""
            권한이 부족합니다.
            roles : $roles
            """
        )
    }

    private fun setUserRepresentation(dto: JoinOrLoginDto): UserRepresentation {
        val userRepresentation = UserRepresentation()
        userRepresentation.isEnabled = true
        userRepresentation.username = dto.username
        return userRepresentation
    }

    private fun setCredentialRepresentation(dto: JoinOrLoginDto): CredentialRepresentation {
        val passwordCred = CredentialRepresentation()
        passwordCred.isTemporary = false
        passwordCred.type = CredentialRepresentation.PASSWORD
        passwordCred.value = dto.password
        return passwordCred
    }

    fun joinKeycloakServer(dto: JoinOrLoginDto) {
        val userRepresentation = setUserRepresentation(dto)
        val realmResource = keycloak.realm(realm)
        val usersResource = realmResource.users()
        usersResource.create(userRepresentation).use { response ->
            if (response.status == 201) {
                val userId = CreatedResponseUtil.getCreatedId(response)
                val passwordCred = setCredentialRepresentation(dto)
                val userResource = usersResource[userId]
                userResource.resetPassword(passwordCred)
                val userRole = "ROLE_USER"
                val realmRoleRep = realmResource.roles()[userRole].toRepresentation()
                userResource.roles().realmLevel().add(listOf(realmRoleRep))
                dto.username = userId + "_keycloak"
                userService.join(dto)
            } else throw JoinKeycloakException("""
                Keycloak 서버 가입에 실패했습니다.
                응답 코드 = ${response.status}
                """
            )
        }
    }

    fun withdrawSso(username: String): Boolean {
        val originUsername = username.replace("_keycloak", "")
        val realmResource = keycloak.realm(realm)
        val usersResource = realmResource.users()
        usersResource.delete(originUsername).use { response ->
            return if (response.status == 204) {
                if (!userService.withdraw(originUsername)) throw KeycloakWithdrawException("""
                    키클락 탈퇴는 성공했으나 회원 탈퇴에 실패했습니다.
                    username = $username
                    """
                )
                true
            } else throw KeycloakWithdrawException("username = $username")
        }
    }
}