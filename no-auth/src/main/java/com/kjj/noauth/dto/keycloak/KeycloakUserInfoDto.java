package com.kjj.noauth.dto.keycloak;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakUserInfoDto {
    private String sub;
    private boolean emailVerified;
    private String[] roles;
    private String name;
    private String preferredUsername;
    private String givenName;
    private String familyName;
}
