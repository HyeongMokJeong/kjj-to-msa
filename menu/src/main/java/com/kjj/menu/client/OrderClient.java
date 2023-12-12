package com.kjj.menu.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderClient {
    private final RestTemplate restTemplate;
    private final ClientUriTool clientUriTool;

    public void deleteAllByMenu(String name) {
        String uri = clientUriTool.getRequestURI("/v1/client/order", Map.of("name", name));
        restTemplate.delete(uri);
    }
}
