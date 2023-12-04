package com.jhm.client;

import com.jhm.dto.JoinDto;
import com.jhm.dto.UserDto;
import com.jhm.tool.ClientUriTool;
import com.jhm.tool.HttpTools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class UserClient {
    private final RestTemplate restTemplate;
    private final ClientUriTool clientUriTool;
    private final HttpTools httpTools;

    @Value("${kjj.client.user.host}") private String userServerHost;

    public UserClient(RestTemplate restTemplate, ClientUriTool clientUriTool, HttpTools httpTools) {
        this.restTemplate = restTemplate;
        this.clientUriTool = clientUriTool;
        this.httpTools = httpTools;
    }

    public UserDto findByUsername(String username) {
        String uri = clientUriTool.getRequestURI(userServerHost, "/v1/user/info/all", Map.of("username", username));
        UserDto userDto = restTemplate.getForObject(uri, UserDto.class);
        return userDto;
    }

    public Boolean existsByUsername(String username) {
        String uri = clientUriTool.getRequestURI(userServerHost, "/v1/user/exist", Map.of("username", username));
        return restTemplate.getForObject(uri, Boolean.class);
    }

    public Boolean join(JoinDto dto) {
        String uri = clientUriTool.getRequestURI(userServerHost, "/v1/user/join");
        return restTemplate.postForObject(uri, httpTools.getRequestEntityTypeJson(dto), Boolean.class);
    }

    public UserDto joinKeycloak(UserDto dto) {
        String uri = clientUriTool.getRequestURI(userServerHost, "/v1/user/join/keycloak");
        return restTemplate.postForObject(uri, httpTools.getRequestEntityTypeJson(dto), UserDto.class);
    }

    public Boolean withdraw(String username) {
        String uri = clientUriTool.getRequestURI(userServerHost, "/v1/user/withdraw", Map.of("username", username));
        return restTemplate.getForObject(uri, Boolean.class);
    }

    public Boolean checkPassword(String username, String password) {
        String uri = clientUriTool.getRequestURI(userServerHost, "/v1/user/check/password", Map.of("username", username, "password", password));
        return restTemplate.getForObject(uri, Boolean.class);
    }

    public void clearPolicyByDeletedMenu(Long menuId) {
        String uri = clientUriTool.getRequestURI(
                userServerHost,
                "/v1/user/policy/menu-del",
                Map.of("id", String.valueOf(menuId)));
        restTemplate.patchForObject(
                uri,
                null,
                Void.class
        );
    }
}
