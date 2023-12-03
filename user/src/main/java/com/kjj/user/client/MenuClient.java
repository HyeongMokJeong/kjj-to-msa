package com.kjj.user.client;

import com.kjj.user.dto.menu.MenuNameAndCostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuClient {

    private final RestTemplate restTemplate;
    @Value("${my.client.menu.host}") private String menuServerHost;

    private String getRequestURI(String path, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder(menuServerHost + path + '?');
        for (Map.Entry<String, String> entry : params.entrySet()) {
            stringBuilder.append(String.join("=", entry.getKey(), entry.getValue()));
            stringBuilder.append("&");
        }
        return stringBuilder.toString();
    }

    public boolean existsById(Long menuId) {
        String uri = getRequestURI("/v1/menu/exist", Map.of("id", String.valueOf(menuId)));
        return restTemplate.getForObject(uri, Boolean.class);
    }

    public MenuNameAndCostDto findNameAndCostById(Long menuId) {
        String uri = getRequestURI("/v1/menu/name-cost", Map.of("id", String.valueOf(menuId)));
        return restTemplate.getForObject(uri, MenuNameAndCostDto.class);
    }

    public String findNameById(Long menuId) {
        String uri = getRequestURI("/v1/menu/name", Map.of("id", String.valueOf(menuId)));
        return restTemplate.getForObject(uri, String.class);
    }
}
