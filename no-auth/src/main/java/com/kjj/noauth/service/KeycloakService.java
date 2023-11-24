package com.kjj.noauth.service;

import com.kjj.noauth.dto.keycloak.KeycloakUserInfoDto;
import com.kjj.noauth.dto.user.JoinDto;
import com.kjj.noauth.exception.JoinKeycloakException;
import com.kjj.noauth.exception.LoginKeycloakException;
import com.kjj.noauth.external.KeycloakProperties;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    @Value("${keycloak.realm}") private String realm;

    private final Keycloak keycloak;
    private final UserService userService;
    private final KeycloakProperties properties;
    private final RestTemplate restTemplate;

    public KeycloakUserInfoDto getUserInfoFromKeycloak(String keycloakToken) {
        String url = properties.getHost() + "/auth/realms/" + properties.getRealmName() + "/protocol/openid-connect/userinfo";
        String tokenPrefix = "Bearer ";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", tokenPrefix + keycloakToken);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<KeycloakUserInfoDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                KeycloakUserInfoDto.class
        );
        KeycloakUserInfoDto body = response.getBody();
        if (body == null) throw new LoginKeycloakException("keycloak에서 유저 정보를 조회할 수 없습니다.");
        keycloak.tokenManager().invalidate(keycloakToken);
        return body;
    }

    public String generateUsernameFromSub(KeycloakUserInfoDto dto) {
        return dto.getSub() + "_keycloak";
    }

    public String checkKeycloakUserRoles(KeycloakUserInfoDto dto) throws LoginKeycloakException {
        String roleUser = "ROLE_USER";
        String roleManager = "ROLE_MANAGER";
        List<String> roles = Arrays.asList(dto.getRoles());
        if (roles.contains(roleManager)) return roleManager;
        else if (roles.contains(roleUser)) return roleUser;
        else throw new LoginKeycloakException("""
                    권한이 부족합니다.
                    roles : """ + roles);
    }

    public void joinKeycloakServer(JoinDto dto) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(dto.getUsername());

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        try (Response response = usersResource.create(userRepresentation)) {
            if (response.getStatus() == 201) {
                String userId = CreatedResponseUtil.getCreatedId(response);
                CredentialRepresentation passwordCred = new CredentialRepresentation();
                passwordCred.setTemporary(false);
                passwordCred.setType(CredentialRepresentation.PASSWORD);
                passwordCred.setValue(dto.getPassword());
                UserResource userResource = usersResource.get(userId);

                userResource.resetPassword(passwordCred);

                String roleName = "ROLE_USER";
                RoleRepresentation realmRoleRep = realmResource.roles().get(roleName).toRepresentation();
                userResource.roles().realmLevel().add(Arrays.asList(realmRoleRep));

                dto.setUsername(userId + "_keycloak");
                userService.join(dto);
            }
            else throw new JoinKeycloakException("""
                    Keycloak 서버 가입에 실패했습니다.
                    응답 코드 : """ + response.getStatus());
        }
    }

}
