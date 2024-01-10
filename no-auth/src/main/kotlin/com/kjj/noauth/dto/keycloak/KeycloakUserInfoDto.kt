package com.kjj.noauth.dto.keycloak

data class KeycloakUserInfoDto(
    val sub: String,
    val emailVerified: Boolean,
    val roles: Array<String>,
    val name: String,
    val preferredUsername: String,
    val givenName: String,
    val familyName: String
) {
}