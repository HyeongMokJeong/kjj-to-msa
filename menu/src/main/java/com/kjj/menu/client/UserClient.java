package com.kjj.menu.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserClient {
    private final RestTemplate restTemplate;
    private final ClientUriTool clientUriTool;

    @Value("${my.client.user.host}") private String userServerHost;

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
