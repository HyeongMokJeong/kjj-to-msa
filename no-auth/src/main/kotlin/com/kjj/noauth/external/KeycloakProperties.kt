package com.kjj.noauth.external

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("my.keycloak")
data class KeycloakProperties(
    var host: String,
    var realmName: String,
    var clientId: String
){
    constructor(): this("", "", "")
}